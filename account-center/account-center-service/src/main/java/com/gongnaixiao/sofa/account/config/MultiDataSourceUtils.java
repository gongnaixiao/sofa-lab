package com.gongnaixiao.sofa.account.config;

import org.apache.commons.lang3.StringUtils;

public class MultiDataSourceUtils {

    public static DataSourceKey getByMagic(String magicNumber) {

        if (!StringUtils.isNumeric(magicNumber)) {
            throw new RuntimeException("Magic number must be numbers only!");
        }

        if (Integer.parseInt(magicNumber) % 2 == 1) {
            return DataSourceKey.ACCOUNT_DB1;
        }
        return DataSourceKey.ACCOUNT_DB2;
    }
}
