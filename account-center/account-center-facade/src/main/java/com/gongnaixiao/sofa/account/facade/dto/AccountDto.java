package com.gongnaixiao.sofa.account.facade.dto;

import java.math.BigDecimal;

public class AccountDto {
    private String accountNo;

    private BigDecimal balance;

    private BigDecimal freezeAmount;

    private BigDecimal unreachAmount;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getFreezeAmount() {
        return freezeAmount;
    }

    public void setFreezeAmount(BigDecimal freezeAmount) {
        this.freezeAmount = freezeAmount;
    }

    public BigDecimal getUnreachAmount() {
        return unreachAmount;
    }

    public void setUnreachAmount(BigDecimal unreachAmount) {
        this.unreachAmount = unreachAmount;
    }
}
