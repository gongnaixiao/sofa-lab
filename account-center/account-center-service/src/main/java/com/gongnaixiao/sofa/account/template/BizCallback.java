package com.gongnaixiao.sofa.account.template;

import org.springframework.transaction.TransactionStatus;

public interface BizCallback {

    /**
     * 基本参数校验
     */
    void checkParameter();

    /**
     * 在TransactionTemplate中执行
     */
    void execute(TransactionStatus status);

}