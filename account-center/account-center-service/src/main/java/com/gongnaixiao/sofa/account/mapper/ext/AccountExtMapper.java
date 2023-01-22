package com.gongnaixiao.sofa.account.mapper.ext;

import com.gongnaixiao.sofa.account.entity.Account;
import com.gongnaixiao.sofa.account.entity.AccountExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountExtMapper {
    void batchInsertAccounts(List<Account> accounts);
}