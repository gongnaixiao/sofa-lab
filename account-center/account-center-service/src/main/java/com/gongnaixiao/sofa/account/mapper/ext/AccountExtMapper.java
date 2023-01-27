package com.gongnaixiao.sofa.account.mapper.ext;

import com.gongnaixiao.sofa.account.entity.Account;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface AccountExtMapper {
    void batchInsertAccounts(List<Account> accounts);

    Account getAccountForUpdate(String accountNo);

    int updateBalance(@Param("accountNo") String accountNo, @Param("amt") BigDecimal amt);
}