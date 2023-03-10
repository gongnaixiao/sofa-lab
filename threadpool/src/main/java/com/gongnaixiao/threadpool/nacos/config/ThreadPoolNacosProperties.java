package com.gongnaixiao.threadpool.nacos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "threadpool.nacos")
public class ThreadPoolNacosProperties {
    private String dataId;

    private String group;

    private String namespace;
}
