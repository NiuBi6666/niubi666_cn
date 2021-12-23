package com.xiuxiuguang.niubi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiuxiuguang.niubi.dao")
public class Niubi666CnApplication {

    public static void main(String[] args) {
        SpringApplication.run(Niubi666CnApplication.class, args);
    }

}
