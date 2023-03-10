package com.gongnaixiao.threadpool.nacos.config;

import com.gongnaixiao.threadpool.nacos.NacosRefresher;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ThreadPoolNacosProperties.class)
public class ThreadPoolNacosConfiguration {
    @Bean
    public NacosRefresher nacosRefresher() {
        return new NacosRefresher();
    }
}
