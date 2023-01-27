package com.gongnaixiao.sofa.account.facade.api;


import com.gongnaixiao.sofa.account.facade.request.AccountTransRequest;
import com.gongnaixiao.sofa.account.facade.result.AccountTransResult;

public interface AcctTransService {
    /**
     * 转账接口 FMT模式
     *
     * @param accountTransRequest 转账请求
     * @return 转账结果
     */
    AccountTransResult transerByAT(AccountTransRequest accountTransRequest);

//    /**
//     * 转账接口 TCC模式
//     *
//     * @param accountTransRequest 转账请求
//     * @return 转账结果
//     */
//    AccountTransResult transerByTcc(AccountTransRequest accountTransRequest);
}