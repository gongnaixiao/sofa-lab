package com.gongnaixiao.sofa.account.facade.result;


import com.gongnaixiao.sofa.account.facade.dto.AccountDto;

public class AccountQueryResult extends AbstractAccountResult {

    private AccountDto account;

    public AccountDto getAccount() {
        return this.account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }
    
}