package com.gongnaixiao.sofa.account.tcc;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.gongnaixiao.sofa.account.entity.Account;
import com.gongnaixiao.sofa.account.facade.request.AccountTransRequest;
import com.gongnaixiao.sofa.account.facade.result.AccountTransResult;
import com.gongnaixiao.sofa.account.facade.tcc.AcctDrawTccService;
import com.gongnaixiao.sofa.account.template.BizCallback;
import com.gongnaixiao.sofa.account.template.BizTemplate;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

// DTX演示：TCC模式，扣款接口实现
@Service
@SofaService(bindings = { @SofaServiceBinding(bindingType = "bolt") })
public class AcctDrawTccServiceImpl extends AcctAbstractTccService implements AcctDrawTccService {

    @Override
    public AccountTransResult debit(AccountTransRequest accountTransRequest,
                                    String shardingKey,
                                    BusinessActionContext businessActionContext) {

        return BizTemplate.executeWithTransaction(accountTransactionTemplate, new BizCallback() {

            @Override
            public void execute(TransactionStatus status) {
                // 记录事务与业务关系，用于二阶段根据事务获取业务信息，以及二阶段的幂等性验证
                connectTxWithBusiness(businessActionContext, accountTransRequest, shardingKey, false);

                // 转账场景下，扣钱是针对发起账户
                Account account = getAccountAndCheck(accountTransRequest.getBacc());

                // 增加冻结金额
                increaseFreezeAmount(account, accountTransRequest.getTxnAmt());

                // 检查余额是否满足
                checkBalance(account);
            }

            @Override
            public void checkParameter() {
            }
        });
    }

    @Override
    public boolean commit(BusinessActionContext businessActionContext) {
        return super.commit(businessActionContext, false);
    }

    @Override
    public boolean rollback(BusinessActionContext businessActionContext) {
        return super.rollback(businessActionContext, false);
    }

}