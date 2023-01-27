package com.gongnaixiao.sofa.account.facade.at;

import com.gongnaixiao.sofa.account.facade.request.AccountTransRequest;
import com.gongnaixiao.sofa.account.facade.result.AccountTransResult;

public interface AcctDepositAtService {
    /**
     * <p>
     * 贷记记账接口 AT模式
     * </p>
     *
     * <p>
     * 结合账户的余额方向来对账户的资金进行流入 or 流出操作
     * </p>
     *
     * @param accountTransRequest   请求对象参数,详情请见{@link AccountTransRequest}
     * @return AccountTransResult 交易处理结果
     */
    public AccountTransResult credit(AccountTransRequest accountTransRequest);
}