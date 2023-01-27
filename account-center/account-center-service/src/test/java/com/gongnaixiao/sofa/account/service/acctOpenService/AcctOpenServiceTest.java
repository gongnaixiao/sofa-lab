package com.gongnaixiao.sofa.account.service.acctOpenService;

import com.gongnaixiao.sofa.account.facade.api.AcctOpenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AcctOpenServiceTest {
    @Autowired
    private AcctOpenService acctOpenService;

    @Test
    public void TestInitAccounts() throws InterruptedException {
        acctOpenService.initAccounts("1234");
        Thread.sleep(1000L);
    }
}
