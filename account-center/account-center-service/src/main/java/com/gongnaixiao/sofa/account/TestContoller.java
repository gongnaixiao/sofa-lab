package com.gongnaixiao.sofa.account;

import com.gongnaixiao.sofa.account.mapper.AccountDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestContoller {
    @Autowired
    AccountDAO accountDAO;


    @GetMapping("/getAccount")
    public void getAccount() {
        accountDAO.getAccount("1");
    }
}
