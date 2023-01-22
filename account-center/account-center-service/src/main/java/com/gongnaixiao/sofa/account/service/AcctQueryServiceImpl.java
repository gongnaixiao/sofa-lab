package com.gongnaixiao.sofa.account.service;

import com.gongnaixiao.sofa.account.entity.Account;
import com.gongnaixiao.sofa.account.enums.CodeEnum;
import com.gongnaixiao.sofa.account.facade.api.AcctQueryService;
import com.gongnaixiao.sofa.account.facade.dto.AccountDto;
import com.gongnaixiao.sofa.account.facade.result.AccountQueryResult;
import com.gongnaixiao.sofa.account.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class AcctQueryServiceImpl implements AcctQueryService {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public AccountQueryResult queryAccount(String accountNo) {
        AccountQueryResult accountQueryResult = new AccountQueryResult();

        try {
            Account account = accountMapper.selectByPrimaryKey(accountNo);

            if (account == null) {
                accountQueryResult.setSuccess(false);
                accountQueryResult.setMsgCode(CodeEnum.ACCOUNT_NULL.getCode());
                accountQueryResult.setMsgText(CodeEnum.ACCOUNT_NULL.getDesc());
            } else {
                accountQueryResult.setSuccess(true);
                accountQueryResult.setAccount(getAccountDto(account));
                accountQueryResult.setMsgCode(CodeEnum.SUCCESS.getCode());
                accountQueryResult.setMsgText(CodeEnum.SUCCESS.getDesc());
            }
        } catch (Exception e) {
            accountQueryResult.setSuccess(false);
            accountQueryResult.setMsgCode(CodeEnum.SYSTEM_EXCEPTION.getCode());
            accountQueryResult.setMsgText(CodeEnum.SYSTEM_EXCEPTION.getDesc());
            accountQueryResult.setErrorMessage(e.getMessage());
        }

        return accountQueryResult;
    }

    private AccountDto getAccountDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNo(account.getAccountNo());
        accountDto.setBalance(account.getBalance());
        accountDto.setFreezeAmount(account.getFreezeAmount());
        accountDto.setUnreachAmount(account.getUnreachAmount());



        return accountDto;
    }

}
