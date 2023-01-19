package com.gongnaixiao.sofa.account;

import com.gongnaixiao.sofa.account.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestContoller {
    @Autowired
    AccountMapper accountDAO;


    @GetMapping("/getAccount")
    public void getAccount() {
        accountDAO.selectByPrimaryKey(1);
    }
}
