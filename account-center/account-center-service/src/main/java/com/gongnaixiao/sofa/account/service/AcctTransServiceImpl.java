package com.gongnaixiao.sofa.account.service;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.gongnaixiao.sofa.account.enums.CodeEnum;
import com.gongnaixiao.sofa.account.facade.api.AcctTransService;
import com.gongnaixiao.sofa.account.facade.at.AcctDepositAtService;
import com.gongnaixiao.sofa.account.facade.at.AcctDrawAtService;
import com.gongnaixiao.sofa.account.facade.request.AccountTransRequest;
import com.gongnaixiao.sofa.account.facade.result.AccountTransResult;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SofaService(bindings = {@SofaServiceBinding(bindingType = "bolt")})
public class AcctTransServiceImpl implements AcctTransService {
    @Autowired
    private AcctDrawAtService acctDrawAtService;
    @Autowired
    private AcctDepositAtService acctDepositAtService;

    @GlobalTransactional
    @Override
    public AccountTransResult transerByAT(AccountTransRequest accountTransRequest) {
        AccountTransResult accountTransResult = null;
        accountTransResult = acctDrawAtService.debit(accountTransRequest);
        if (!accountTransResult.isSuccess()) {
            // 事务回滚
            throw new RuntimeException("debit fmt failed.");
        }
        accountTransResult = acctDepositAtService.credit(accountTransRequest);
        if (!accountTransResult.isSuccess()) {
            // 事务回滚
            throw new RuntimeException("credit fmt failed.");
        }
        accountTransResult.setMsgCode(CodeEnum.SUCCESS.getCode());
        accountTransResult.setMsgText(CodeEnum.SUCCESS.getDesc());
        return accountTransResult;
    }
}
