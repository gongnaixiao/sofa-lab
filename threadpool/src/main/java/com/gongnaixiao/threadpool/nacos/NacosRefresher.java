package com.gongnaixiao.threadpool.nacos;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.gongnaixiao.threadpool.nacos.config.ThreadPoolNacosProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class NacosRefresher implements InitializingBean, Listener {
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(100);

    @NacosInjected
    private ConfigService configService;

    @Autowired
    private ThreadPoolNacosProperties threadPoolNacosProperties;

    @Override
    public Executor getExecutor() {
        return null;
    }

    @Override
    public void receiveConfigInfo(String content) {
        log.info("receive from nacos:[{}]", content);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        String dataId = this.threadPoolNacosProperties.getDataId();
        String group = this.threadPoolNacosProperties.getGroup();

        try {
            configService.addListener(dataId, group, this);
            log.info("add listener success, dataId: {}, group: {}", dataId, group);
        } catch (NacosException e) {
            log.error("add listener error, dataId: {}, group: {}", dataId, group, e);

        }
    }

}
