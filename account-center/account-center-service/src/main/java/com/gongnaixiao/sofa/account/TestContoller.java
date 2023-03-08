package com.gongnaixiao.sofa.account;

import com.alibaba.nacos.common.utils.UuidUtils;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.gongnaixiao.sofa.account.facade.api.AcctOpenService;
import com.gongnaixiao.sofa.account.facade.api.AcctTransService;
import com.gongnaixiao.sofa.account.facade.request.AccountTransRequest;
import com.gongnaixiao.sofa.account.facade.result.AccountTransResult;
import com.gongnaixiao.sofa.account.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

@RestController
public class TestContoller {
    @Autowired
    AccountMapper accountDAO;

    @SofaReference(interfaceType = AcctOpenService.class, binding = @SofaReferenceBinding(bindingType = "bolt"), jvmFirst = false)
    private AcctOpenService acctOpenService;

    //@SofaReference(interfaceType = AcctTransService.class, binding = @SofaReferenceBinding(bindingType = "bolt"), jvmFirst = false)
    @Autowired
    private AcctTransService acctTransService;

    @GetMapping("/getAccount")
    public void getAccount() {
        accountDAO.selectByPrimaryKey("1");
    }

    @PostMapping("/openAcct")
    public void openAcct(String magicNumber) {
        acctOpenService.initAccounts(magicNumber);
    }

    @GetMapping("transerByAT")
    public void transerByAT() {
        AccountTransRequest accountTransRequest = new AccountTransRequest();
        accountTransRequest.setBacc("00222200");
        accountTransRequest.setPeerBacc("00111100");
        accountTransRequest.setTxnAmt(new BigDecimal("10"));
        accountTransRequest.setTxnSn(UuidUtils.generateUuid());
        accountTransRequest.setTxnTime(new Date());

        AccountTransResult accountTransResult = acctTransService.transerByAT(accountTransRequest);
        System.out.println(accountTransResult);
    }
    @GetMapping("transerByATFail")
    public void transerByATFail() {
        AccountTransRequest accountTransRequest = new AccountTransRequest();
        accountTransRequest.setBacc("00222200");
        accountTransRequest.setPeerBacc("00333300");
        accountTransRequest.setTxnAmt(new BigDecimal("10"));
        accountTransRequest.setTxnSn(UuidUtils.generateUuid());
        accountTransRequest.setTxnTime(new Date());

        AccountTransResult accountTransResult = acctTransService.transerByAT(accountTransRequest);
        System.out.println(accountTransResult);
    }


    @GetMapping("transerByTcc")
    public void transerByTcc() {
        AccountTransRequest accountTransRequest = new AccountTransRequest();
        accountTransRequest.setBacc("00222201");
        accountTransRequest.setPeerBacc("00111101");
        accountTransRequest.setTxnAmt(new BigDecimal("10"));
        accountTransRequest.setTxnSn(UuidUtils.generateUuid());
        accountTransRequest.setTxnTime(new Date());

        AccountTransResult accountTransResult = acctTransService.transerByTcc(accountTransRequest);
        System.out.println(accountTransResult);
    }
}
