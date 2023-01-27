package com.gongnaixiao.sofa.account.mapper.accountMapper;

import com.gongnaixiao.sofa.account.entity.Account;
import com.gongnaixiao.sofa.account.mapper.AccountMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class AccountMapperTest {
    @Autowired
    AccountMapper accountMapper;

    @Test
    public void inserTest() {
        Account account = new Account();
        account.setAccountNo("20000");
        account.setBalance(BigDecimal.valueOf(100.01));
        account.setFreezeAmount(BigDecimal.valueOf(99.09));
        account.setUnreachAmount(BigDecimal.valueOf(50));

        accountMapper.insert(account);
    }
}
