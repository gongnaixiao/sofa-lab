package com.gongnaxiao.practice.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class ConfigurationTest {
    @Test
    public void testConfigBean() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ConfigBean.class);
        for (String beanName : context.getBeanDefinitionNames()) {
            String[] aliases = context.getAliases(beanName);
            System.out.println(String.format("bean名称:%s,别名:%s,bean对象:%s",
                    beanName,
                    Arrays.asList(aliases),
                    context.getBean(beanName)));
        }
    }
}
