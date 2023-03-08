package com.gongnaixiao.sofa.account.tcc;

import com.gongnaixiao.sofa.account.config.DynamicDataSourceContextHolder;
import com.gongnaixiao.sofa.account.config.MultiDataSourceUtils;
import com.gongnaixiao.sofa.account.entity.Account;
import com.gongnaixiao.sofa.account.entity.AccountTransaction;
import com.gongnaixiao.sofa.account.entity.AccountTransactionExample;
import com.gongnaixiao.sofa.account.enums.CodeEnum;
import com.gongnaixiao.sofa.account.enums.OperationEnum;
import com.gongnaixiao.sofa.account.enums.StatusEnum;
import com.gongnaixiao.sofa.account.exception.AcctCenterException;
import com.gongnaixiao.sofa.account.facade.request.AccountTransRequest;
import com.gongnaixiao.sofa.account.facade.result.AccountTransResult;
import com.gongnaixiao.sofa.account.mapper.AccountMapper;
import com.gongnaixiao.sofa.account.mapper.AccountTransactionMapper;
import com.gongnaixiao.sofa.account.mapper.ext.AccountExtMapper;
import com.gongnaixiao.sofa.account.template.BizCallback;
import com.gongnaixiao.sofa.account.template.BizTemplate;
import io.seata.common.exception.FrameworkException;
import io.seata.core.context.RootContext;
import io.seata.rm.DefaultResourceManager;
import io.seata.rm.tcc.TCCResource;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import io.seata.rm.tcc.remoting.RemotingDesc;
import io.seata.rm.tcc.remoting.RemotingParser;
import io.seata.rm.tcc.remoting.parser.DefaultRemotingParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import java.lang.reflect.Method;
import java.math.BigDecimal;

public abstract class AcctAbstractTccService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AcctAbstractTccService.class);

    @Autowired
    protected AccountTransactionMapper accountTransactionMapper;

    @Autowired
    protected AccountMapper accountMapper;

    @Autowired
    protected AccountExtMapper accountExtMapper;

    @Autowired
    protected TransactionTemplate accountTransactionTemplate;

    protected boolean commit(BusinessActionContext businessActionContext, boolean isCredit) {
        String targetAccount = (String) businessActionContext.getActionContext("shardingKey");
        DynamicDataSourceContextHolder.setDataSourceKey(MultiDataSourceUtils.getByMagic(targetAccount.substring(2, 4)));
        LOGGER.info("当前 db: {}", DynamicDataSourceContextHolder.getDataSourceKey());
        LOGGER.info("当前 XID: {}", RootContext.getXID());

        AccountTransResult result = BizTemplate.executeWithTransaction(accountTransactionTemplate, new BizCallback() {

            @Override
            public void execute(TransactionStatus status) {
                // 根据txid获取当前交易信息
                AccountTransaction acctTrans = getAccountTransaction(businessActionContext);

                // 幂等性验证
                checkIdempotent(acctTrans, StatusEnum.DONE);

                // 更新余额
                Account account = getAccountAndCheck(acctTrans.getAccountNo());
                if (isCredit) {
                    // 增加余额
                    addBalance(account, acctTrans.getAmount());
                    // 释放未达金额
                    releaseUnreachAmount(account, acctTrans.getAmount());
                } else {
                    // 扣除余额
                    subtractBalance(account, acctTrans.getAmount());
                    // 释放冻结金额
                    releaseFreezeAmount(account, acctTrans.getAmount());
                }

                // 更新事务业务状态
                updateTransactionStatus(acctTrans, StatusEnum.DONE);
            }

            @Override
            public void checkParameter() {
                // TODO Auto-generated method stub

            }
        });

        return result.isSuccess();
    }

    protected boolean rollback(BusinessActionContext businessActionContext, boolean isCredit) {
        String targetAccount = (String) businessActionContext.getActionContext("shardingKey");
        DynamicDataSourceContextHolder.setDataSourceKey(MultiDataSourceUtils.getByMagic(targetAccount.substring(2, 4)));
        LOGGER.info("当前 db: {}", DynamicDataSourceContextHolder.getDataSourceKey());
        LOGGER.info("当前 XID: {}", RootContext.getXID());
        AccountTransResult result = BizTemplate.executeWithTransaction(accountTransactionTemplate, new BizCallback() {

            @Override
            public void execute(TransactionStatus status) {
                // 根据txid获取当前交易信息
                AccountTransaction acctTrans = getAccountTransaction(businessActionContext);
                // 首先检查分支事务是否存在，如果不存在，则直接返回真，不再处理，视为空回滚
                if (acctTrans == null) {
                    return;
                }

                // 幂等性验证
                checkIdempotent(acctTrans, StatusEnum.FAIL);

                Account account = getAccountAndCheck(acctTrans.getAccountNo());
                if (isCredit) {
                    // 释放未达金额
                    releaseUnreachAmount(account, acctTrans.getAmount());
                } else {
                    // 释放冻结金额
                    releaseFreezeAmount(account, acctTrans.getAmount());
                }

                // 更新事务业务状态
                updateTransactionStatus(acctTrans, StatusEnum.FAIL);
            }

            @Override
            public void checkParameter() {
                // TODO Auto-generated method stub

            }
        });

        return result.isSuccess();
    }

    protected void connectTxWithBusiness(BusinessActionContext businessActionContext,
                                         AccountTransRequest accountTransRequest, String shardingKey, boolean isCredit) {

        String txid = businessActionContext.getXid() + businessActionContext.getBranchId();
        LOGGER.info("{}: connect tx [{}] with account [{}]", isCredit ? "CREDIT" : "DEBIT", txid,
                isCredit ? accountTransRequest.getPeerBacc() : accountTransRequest.getBacc());
        addAccountTransaction(accountTransRequest, txid, shardingKey, isCredit);
    }

    protected AccountTransaction getAccountTransaction(BusinessActionContext businessActionContext) {
        String txid = businessActionContext.getXid() + businessActionContext.getBranchId();
        String shardingKey = (String) businessActionContext.getActionContext("shardingKey");
        LOGGER.info("get account transaction by tx: [{}] and shardingkey: [{}]", txid, shardingKey);
        AccountTransactionExample accountTransactionExample = new AccountTransactionExample();
        accountTransactionExample.createCriteria().andTxIdEqualTo(txid).andShardingKeyEqualTo(shardingKey);
        AccountTransaction acctTrans = accountTransactionMapper.selectByExample(accountTransactionExample).get(0);

        return acctTrans;
    }

    protected void updateTransactionStatus(AccountTransaction acctTrans, StatusEnum status) {
        LOGGER.info("update account transaction [{}] status [{}]", acctTrans.getTxId(), status.getCode());
        acctTrans.setStatus(status.getCode());
        accountTransactionMapper.updateByPrimaryKey(acctTrans);
    }

    protected Account getAccountAndCheck(String targetAccount) {
        Account account = accountExtMapper.getAccountForUpdate(targetAccount);

        // 检查账户
        if (account == null) {
            throw new AcctCenterException(CodeEnum.ACCOUNT_NULL);
        }
        return account;
    }

    protected void releaseUnreachAmount(Account account, BigDecimal amount) {
        updateUnreach(account, amount, false);
    }

    protected void increaseUnreachAmount(Account account, BigDecimal amount) {
        updateUnreach(account, amount, true);
    }

    protected void increaseFreezeAmount(Account account, BigDecimal amount) {
        updateFreeze(account, amount, true);
    }

    protected void releaseFreezeAmount(Account account, BigDecimal amount) {
        updateFreeze(account, amount, false);
    }

    protected void checkBalance(Account account) {
        BigDecimal availableAmount = account.getBalance().subtract(account.getFreezeAmount());
        if (availableAmount.compareTo(BigDecimal.ZERO) < 0) {
            LOGGER.error("account balance not enough");
            throw new AcctCenterException(CodeEnum.ACCOUNT_BALANCE_NOT_ENOUGH);
        }
    }

    protected void addBalance(Account account, BigDecimal amount) {
        updateBalance(account, amount, true);
    }

    protected void subtractBalance(Account account, BigDecimal amount) {
        updateBalance(account, amount, false);
    }

    protected void checkIdempotent(AccountTransaction acctTrans, StatusEnum expectedStatus) {
        if (acctTrans.getStatus() == StatusEnum.FAIL.getCode()) {
            throw new AcctCenterException(CodeEnum.IDEMPOTENT_EXCEPTION);
        }
    }

    private void addAccountTransaction(AccountTransRequest accountTransRequest, String txId, String shardingKey,
                                       boolean credit) {
        AccountTransaction acctTrans = new AccountTransaction();

        acctTrans.setTxId(txId);
        if (credit) {
            acctTrans.setAccountNo(accountTransRequest.getPeerBacc());
            acctTrans.setOperation(OperationEnum.CREDIT.getCode());
        } else {
            acctTrans.setAccountNo(accountTransRequest.getBacc());
            acctTrans.setOperation(OperationEnum.DEBIT.getCode());
        }
        acctTrans.setAmount(accountTransRequest.getTxnAmt());
        acctTrans.setStatus(StatusEnum.INIT.getCode());
        acctTrans.setShardingKey(shardingKey);

        accountTransactionMapper.insertSelective(acctTrans);
    }

    private void updateUnreach(Account account, BigDecimal amount, boolean add) {
        BigDecimal newUnreachAmount = add ? account.getUnreachAmount().add(amount)
                : account.getUnreachAmount().subtract(amount);
        account.setUnreachAmount(newUnreachAmount);
        accountMapper.updateByPrimaryKey(account);
    }

    private void updateFreeze(Account account, BigDecimal amount, boolean add) {
        BigDecimal newFreezeAmount = add ? account.getFreezeAmount().add(amount)
                : account.getFreezeAmount().subtract(amount);
        account.setFreezeAmount(newFreezeAmount);
        accountMapper.updateByPrimaryKeySelective(account);
    }

    private void updateBalance(Account account, BigDecimal amount, boolean add) {
        BigDecimal newBalance = add ? account.getBalance().add(amount) : account.getBalance().subtract(amount);
        account.setBalance(newBalance);
        accountMapper.updateByPrimaryKey(account);
    }

/*    private void registerResource() {
        RemotingParser remotingParser = DefaultRemotingParser.get().isRemoting(bean, beanName);
        RemotingDesc remotingBeanDesc = remotingParser.getServiceDesc(bean, beanName);
        if (remotingBeanDesc == null) {
            return null;
        }
        if (remotingParser.isService(bean, beanName)) {
            try {
                //service bean, registry resource
                Object targetBean = remotingBeanDesc.getTargetBean();
                for (Method m : methods) {
                    TwoPhaseBusinessAction twoPhaseBusinessAction = m.getAnnotation(TwoPhaseBusinessAction.class);
                    if (twoPhaseBusinessAction != null) {
                        TCCResource tccResource = new TCCResource();
                        tccResource.setActionName(twoPhaseBusinessAction.name());
                        tccResource.setTargetBean(targetBean);
                        tccResource.setPrepareMethod(m);
                        tccResource.setCommitMethodName(twoPhaseBusinessAction.commitMethod());
                        tccResource.setCommitMethod(serviceClass.getMethod(twoPhaseBusinessAction.commitMethod(),
                                twoPhaseBusinessAction.commitArgsClasses()));
                        tccResource.setRollbackMethodName(twoPhaseBusinessAction.rollbackMethod());
                        tccResource.setRollbackMethod(serviceClass.getMethod(twoPhaseBusinessAction.rollbackMethod(),
                                twoPhaseBusinessAction.rollbackArgsClasses()));
                        // set argsClasses
                        tccResource.setCommitArgsClasses(twoPhaseBusinessAction.commitArgsClasses());
                        tccResource.setRollbackArgsClasses(twoPhaseBusinessAction.rollbackArgsClasses());
                        // set phase two method's keys
                        tccResource.setPhaseTwoCommitKeys(this.getTwoPhaseArgs(tccResource.getCommitMethod(),
                                twoPhaseBusinessAction.commitArgsClasses()));
                        tccResource.setPhaseTwoRollbackKeys(this.getTwoPhaseArgs(tccResource.getRollbackMethod(),
                                twoPhaseBusinessAction.rollbackArgsClasses()));
                        //registry tcc resource
                        DefaultResourceManager.get().registerResource(tccResource);
                    }
                }
            } catch (Throwable t) {
                throw new FrameworkException(t, "parser remoting service error");
            }
        }
    }*/
}