package com.gongnaixiao.sofa.account.at;

import com.gongnaixiao.sofa.account.entity.Account;
import com.gongnaixiao.sofa.account.enums.CodeEnum;
import com.gongnaixiao.sofa.account.exception.AcctCenterException;
import com.gongnaixiao.sofa.account.facade.at.AcctDepositAtService;
import com.gongnaixiao.sofa.account.facade.request.AccountTransRequest;
import com.gongnaixiao.sofa.account.facade.result.AccountTransResult;
import com.gongnaixiao.sofa.account.mapper.ext.AccountExtMapper;
import com.gongnaixiao.sofa.account.template.BizCallback;
import com.gongnaixiao.sofa.account.template.BizTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

public class AcctDepositAtServiceImpl implements AcctDepositAtService {

	private final static Logger LOGGER = LoggerFactory.getLogger(AcctDepositAtServiceImpl.class);

	@Autowired
	private AccountExtMapper accountExtMapper;

	@Autowired
	private TransactionTemplate accountTransactionTemplate;

	@Override
	public AccountTransResult credit(AccountTransRequest accountTransRequest) {

		return BizTemplate.executeWithTransaction(accountTransactionTemplate, new BizCallback() {

			@Override
			public void execute(TransactionStatus status) {
				// 转账场景下，加钱是给对方账户
				String targetAccount = accountTransRequest.getPeerBacc();
				LOGGER.info("balance_add getAccountForUpdate {}", targetAccount);
				Account account = accountExtMapper.getAccountForUpdate(targetAccount);

				// 检查账户
				if (account == null) {
					throw new AcctCenterException(CodeEnum.ACCOUNT_NULL);
				}

				// 加钱
				BigDecimal newAmount = account.getBalance().add(accountTransRequest.getTxnAmt());
				account.setBalance(newAmount);
				accountExtMapper.updateBalance(targetAccount, accountTransRequest.getTxnAmt());
				LOGGER.info("balance_add updateAmount {}", targetAccount);
			}

			@Override
			public void checkParameter() {
				// TODO Auto-generated method stub

			}
		});
	}

}