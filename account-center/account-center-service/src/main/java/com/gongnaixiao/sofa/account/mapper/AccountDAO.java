package com.gongnaixiao.sofa.account.mapper;

import com.gongnaixiao.sofa.account.mapper.entity.Account;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface AccountDAO {

    void addAccount(Account account) throws DataAccessException;

    int updateBalance(Account account) throws DataAccessException;

    int updateFreezeAmount(Account account) throws DataAccessException;

    int updateUnreachAmount(Account account) throws DataAccessException;

    Account getAccount(String accountNo) throws DataAccessException;

    Account getAccountForUpdate(String accountNo) throws DataAccessException;

    void deleteAllAccount() throws DataAccessException;

    List<Account> getAccountByRange(String shard, int start, int end) throws DataAccessException;

    int getMaxId(String shard) throws DataAccessException;

    void batchInsertAccounts(List<Account> accounts);
}