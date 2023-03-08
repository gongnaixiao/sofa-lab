package com.gongnaixiao.sofa.account.service;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.gongnaixiao.sofa.account.enums.CodeEnum;
import com.gongnaixiao.sofa.account.facade.api.AcctTransService;
import com.gongnaixiao.sofa.account.facade.at.AcctDepositAtService;
import com.gongnaixiao.sofa.account.facade.at.AcctDrawAtService;
import com.gongnaixiao.sofa.account.facade.request.AccountTransRequest;
import com.gongnaixiao.sofa.account.facade.result.AccountTransResult;
import com.gongnaixiao.sofa.account.facade.tcc.AcctDepositTccService;
import com.gongnaixiao.sofa.account.facade.tcc.AcctDrawTccService;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
//@SofaService(bindings = {@SofaServiceBinding(bindingType = "bolt")})
public class AcctTransServiceImpl implements AcctTransService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AcctTransServiceImpl.class);

    @SofaReference(interfaceType = AcctDrawAtService.class, binding = @SofaReferenceBinding(bindingType = "bolt"), jvmFirst = false)
    private AcctDrawAtService acctDrawAtService;
    @SofaReference(interfaceType = AcctDepositAtService.class, binding = @SofaReferenceBinding(bindingType = "bolt"), jvmFirst = false)
    private AcctDepositAtService acctDepositAtService;

    @SofaReference(interfaceType = AcctDrawTccService.class, binding = @SofaReferenceBinding(bindingType = "bolt"), jvmFirst = false)
    private AcctDrawTccService acctDrawTccService;
    @SofaReference(interfaceType = AcctDepositTccService.class, binding = @SofaReferenceBinding(bindingType = "bolt"), jvmFirst = false)
    private AcctDepositTccService acctDepositTccService;

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

    @Override
    @GlobalTransactional
    public AccountTransResult transerByTcc(AccountTransRequest accountTransRequest) {
        LOGGER.info("transfer by tcc started......");
        AccountTransResult accountTransResult = null;

        // 转出
        accountTransResult = acctDrawTccService.debit(null, accountTransRequest, accountTransRequest.getBacc());
        if (!accountTransResult.isSuccess()) {
            LOGGER.error("debit tcc failed: {}", accountTransResult.getErrorMessage());
            throw new RuntimeException("debit tcc failed.");
        }

        // 转入
        accountTransResult = acctDepositTccService.credit(null, accountTransRequest, accountTransRequest.getPeerBacc());
        if (!accountTransResult.isSuccess()) {
            LOGGER.error("credit tcc failed: {}", accountTransResult.getErrorMessage());
            throw new RuntimeException("credit tcc failed.");
        }

        return accountTransResult;
    }
}
