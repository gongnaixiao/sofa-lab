package com.gongnaixiao.sofa.account.mapper;

import com.gongnaixiao.sofa.account.entity.Account;
import com.gongnaixiao.sofa.account.entity.AccountExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

public interface AccountMapper {
    long countByExample(AccountExample example);

    int deleteByExample(AccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Account row);

    int insertSelective(Account row);

    List<Account> selectByExample(AccountExample example);

    Account selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") Account row, @Param("example") AccountExample example);

    int updateByExample(@Param("row") Account row, @Param("example") AccountExample example);

    int updateByPrimaryKeySelective(Account row);

    int updateByPrimaryKey(Account row);
}