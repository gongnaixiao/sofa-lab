package com.gongnaixiao.sofa.account.mapper;

import com.gongnaixiao.sofa.account.entity.DtxTccAction;
import com.gongnaixiao.sofa.account.entity.DtxTccActionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DtxTccActionMapper {
    long countByExample(DtxTccActionExample example);

    int deleteByExample(DtxTccActionExample example);

    int deleteByPrimaryKey(String actionId);

    int insert(DtxTccAction row);

    int insertSelective(DtxTccAction row);

    List<DtxTccAction> selectByExample(DtxTccActionExample example);

    DtxTccAction selectByPrimaryKey(String actionId);

    int updateByExampleSelective(@Param("row") DtxTccAction row, @Param("example") DtxTccActionExample example);

    int updateByExample(@Param("row") DtxTccAction row, @Param("example") DtxTccActionExample example);

    int updateByPrimaryKeySelective(DtxTccAction row);

    int updateByPrimaryKey(DtxTccAction row);
}