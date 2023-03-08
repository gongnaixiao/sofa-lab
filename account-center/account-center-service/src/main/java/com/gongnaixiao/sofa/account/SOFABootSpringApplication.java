package com.gongnaixiao.sofa.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@MapperScan("com.gongnaixiao.sofa.account.mapper")
public class SOFABootSpringApplication{
    public static void main(String[] args) {
        SpringApplication.run(SOFABootSpringApplication.class, args);
    }
}