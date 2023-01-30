package com.gongnaixiao.sofa.account.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

@SpringBootTest
public class SpringBeanTest {
    @Resource
    ApplicationContext applicationContext;

    @Test
    public void getBeanTest() {
        Object testContoller = applicationContext.getBean("testContoller");
        System.out.println("");
    }
}
