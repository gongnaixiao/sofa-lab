package com.gongnaixiao.sofa.account.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;

import io.seata.rm.datasource.DataSourceProxy;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author HelloWoodes
 */
@Configuration
public class DataSourceProxyConfig {

    @Bean("originAccountDB")
    @ConfigurationProperties(prefix = "spring.datasource.account-db")
    public DataSource dataSourceMaster() {
        return new DruidDataSource();
    }

    @Bean("originAccountDB1")
    @ConfigurationProperties(prefix = "spring.datasource.account-db1")
    public DataSource dataSourceDB1() {
        return new DruidDataSource();
    }

    @Bean("originAccountDB2")
    @ConfigurationProperties(prefix = "spring.datasource.account-db2")
    public DataSource dataSourceDB2() {
        return new DruidDataSource();
    }

    @Bean(name = "accountDB")
    public DataSourceProxy dataSourceProxyMaster(@Qualifier("originAccountDB") DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    @Bean(name = "accountDB1")
    public DataSourceProxy DataSourceProxyDB1(@Qualifier("originAccountDB1") DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    @Bean(name = "accountDB2")
    public DataSourceProxy DataSourceProxyDB2(@Qualifier("originAccountDB2") DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    @Primary
    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource(@Qualifier("accountDB") DataSource dataSourceMaster,
                                        @Qualifier("accountDB1") DataSource dataSourceDB1,
                                        @Qualifier("accountDB2") DataSource dataSourceDB2) {

        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>(3);
        dataSourceMap.put(DataSourceKey.ACCOUNT_DB.name(), dataSourceMaster);
        dataSourceMap.put(DataSourceKey.ACCOUNT_DB1.name(), dataSourceDB1);
        dataSourceMap.put(DataSourceKey.ACCOUNT_DB2.name(), dataSourceDB2);

        dynamicRoutingDataSource.setDefaultTargetDataSource(dataSourceMaster);
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);

        DynamicDataSourceContextHolder.getDataSourceKeys().addAll(dataSourceMap.keySet());

        return dynamicRoutingDataSource;
    }

    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("dynamicDataSource") DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }

}