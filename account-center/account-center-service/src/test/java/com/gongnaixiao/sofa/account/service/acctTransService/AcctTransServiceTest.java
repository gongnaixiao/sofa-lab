package com.gongnaixiao.sofa.account.service.acctTransService;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.gongnaixiao.sofa.account.facade.api.AcctTransService;
import com.gongnaixiao.sofa.account.facade.request.AccountTransRequest;
import com.gongnaixiao.sofa.account.facade.result.AccountTransResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest
public class AcctTransServiceTest {
    @SofaReference(interfaceType = AcctTransService.class, binding = @SofaReferenceBinding(bindingType = "bolt"), jvmFirst = false)
    AcctTransService acctTransService;

    @Test
    public void testTranserByAT() {
        AccountTransRequest accountTransRequest = new AccountTransRequest();
        accountTransRequest.setBacc("00123400");
        accountTransRequest.setPeerBacc("00123401");
        accountTransRequest.setTxnAmt(BigDecimal.valueOf(10L));
        accountTransRequest.setTxnSn(RandomStringUtils.random(8));
        accountTransRequest.setTxnTime(new Date());

        AccountTransResult accountTransResult = acctTransService.transerByAT(accountTransRequest);
        Assertions.assertEquals(accountTransResult.isSuccess(), true);
    }
}
