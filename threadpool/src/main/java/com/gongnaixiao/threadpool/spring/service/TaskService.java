package com.gongnaixiao.threadpool.spring.service;

import com.gongnaixiao.threadpool.spring.constants.PJThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class TaskService {
   @Autowired
   ApplicationContext applicationContext;

    @Scheduled(cron = "0/5 * * * * ? ")
    public void loopExecute() {
        log.info("now[{}]", LocalDateTime.now());
    }

    @Async(PJThreadPool.BACKGROUND_POOL)
    public void asyncExecute() {
        log.info("async execute:[{}]", Thread.currentThread().getName());
    }
}
