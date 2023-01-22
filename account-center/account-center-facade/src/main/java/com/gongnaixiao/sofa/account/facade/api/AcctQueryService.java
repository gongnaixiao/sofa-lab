package com.gongnaixiao.sofa.account.facade.api;


import com.gongnaixiao.sofa.account.facade.result.AccountQueryResult;

public interface AcctQueryService {

    AccountQueryResult queryAccount(String accountNo);

}