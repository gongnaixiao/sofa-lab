package com.gongnaixiao.sofa.account.at;

import com.gongnaixiao.sofa.account.entity.Account;
import com.gongnaixiao.sofa.account.enums.CodeEnum;
import com.gongnaixiao.sofa.account.exception.AcctCenterException;
import com.gongnaixiao.sofa.account.facade.at.AcctDrawAtService;
import com.gongnaixiao.sofa.account.facade.request.AccountTransRequest;
import com.gongnaixiao.sofa.account.facade.result.AccountTransResult;
import com.gongnaixiao.sofa.account.mapper.ext.AccountExtMapper;
import com.gongnaixiao.sofa.account.template.BizCallback;
import com.gongnaixiao.sofa.account.template.BizTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;
import java.math.BigDecimal;

@Service
public class AcctDrawAtServiceImpl implements AcctDrawAtService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AcctDrawAtServiceImpl.class);

    @Autowired
    private AccountExtMapper accountExtMapper;

    @Autowired
    private TransactionTemplate accountTransactionTemplate;

    @Override
    public AccountTransResult debit(AccountTransRequest accountTransRequest) {

        return BizTemplate.executeWithTransaction(accountTransactionTemplate, new BizCallback() {

            @Override
            public void execute(TransactionStatus status) {
                // 转账场景下，扣钱是针对发起账户
                String targetAccount = accountTransRequest.getBacc();
                BigDecimal targetAmount = accountTransRequest.getTxnAmt();
                LOGGER.info("balance_subtract getAccountForUpdate {}", targetAccount);
                Account account = accountExtMapper.getAccountForUpdate(targetAccount);

                // 检查账户
                if (account == null) {
                    throw new AcctCenterException(CodeEnum.ACCOUNT_NULL);
                }

                // 检查余额
                BigDecimal newAmount = account.getBalance().subtract(targetAmount);
                if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
                    throw new AcctCenterException(CodeEnum.ACCOUNT_BALANCE_NOT_ENOUGH);
                }

                // 扣钱
                account.setBalance(newAmount);
                accountExtMapper.updateBalance(targetAccount, targetAmount.negate());
                LOGGER.info("balance_subtract updateAmount {}", targetAccount);
            }

            @Override
            public void checkParameter() {
                // TODO Auto-generated method stub

            }
        });
    }

}