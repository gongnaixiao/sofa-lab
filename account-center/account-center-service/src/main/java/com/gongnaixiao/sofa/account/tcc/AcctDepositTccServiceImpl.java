package com.gongnaixiao.sofa.account.tcc;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.gongnaixiao.sofa.account.entity.Account;
import com.gongnaixiao.sofa.account.facade.request.AccountTransRequest;
import com.gongnaixiao.sofa.account.facade.result.AccountTransResult;
import com.gongnaixiao.sofa.account.facade.tcc.AcctDepositTccService;
import com.gongnaixiao.sofa.account.template.BizCallback;
import com.gongnaixiao.sofa.account.template.BizTemplate;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

// DTX演示：TCC模式，加钱接口实现
@Service
@SofaService(bindings = {@SofaServiceBinding(bindingType = "bolt")})
public class AcctDepositTccServiceImpl extends AcctAbstractTccService implements AcctDepositTccService {

    @Override
    public AccountTransResult credit(AccountTransRequest accountTransRequest, String shardingKey,
                                     BusinessActionContext businessActionContext) {

        return BizTemplate.executeWithTransaction(accountTransactionTemplate, new BizCallback() {

            @Override
            public void execute(TransactionStatus status) {
                // 记录事务与业务关系，用于二阶段根据事务获取业务信息，以及二阶段的幂等性验证
                connectTxWithBusiness(businessActionContext, accountTransRequest, shardingKey, true);

                // 转账场景下，加钱是给对方账户
                Account account = getAccountAndCheck(accountTransRequest.getPeerBacc());

                // 增加未到达金额
                increaseUnreachAmount(account, accountTransRequest.getTxnAmt());
            }

            @Override
            public void checkParameter() {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    public boolean commit(BusinessActionContext businessActionContext) {
        return super.commit(businessActionContext, true);
    }

    @Override
    public boolean rollback(BusinessActionContext businessActionContext) {
        return super.rollback(businessActionContext, true);
    }

}