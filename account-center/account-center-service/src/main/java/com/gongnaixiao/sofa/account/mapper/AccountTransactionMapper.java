package com.gongnaixiao.sofa.account.mapper;

import com.gongnaixiao.sofa.account.entity.AccountTransaction;
import com.gongnaixiao.sofa.account.entity.AccountTransactionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountTransactionMapper {
    long countByExample(AccountTransactionExample example);

    int deleteByExample(AccountTransactionExample example);

    int deleteByPrimaryKey(String txId);

    int insert(AccountTransaction row);

    int insertSelective(AccountTransaction row);

    List<AccountTransaction> selectByExample(AccountTransactionExample example);

    AccountTransaction selectByPrimaryKey(String txId);

    int updateByExampleSelective(@Param("row") AccountTransaction row, @Param("example") AccountTransactionExample example);

    int updateByExample(@Param("row") AccountTransaction row, @Param("example") AccountTransactionExample example);

    int updateByPrimaryKeySelective(AccountTransaction row);

    int updateByPrimaryKey(AccountTransaction row);
}