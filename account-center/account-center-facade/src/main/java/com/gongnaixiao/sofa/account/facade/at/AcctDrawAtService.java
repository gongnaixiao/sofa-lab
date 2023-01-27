package com.gongnaixiao.sofa.account.facade.at;

import com.gongnaixiao.sofa.account.facade.request.AccountTransRequest;
import com.gongnaixiao.sofa.account.facade.result.AccountTransResult;

public interface AcctDrawAtService {
     /**
     * <p>
     * 借记记账接口 FMT模式
     * </p>
     *
     * <p>
     * 根据账户的余额方向来对账户的资金进行流入 or 流出操作
     * </p>
     *
     * @param accountTransRequest   请求对象参数,详情请见{@link AccountTransRequest}
     * @return AccountTransResult 交易处理结果
     */
    public AccountTransResult debit(AccountTransRequest accountTransRequest);
}