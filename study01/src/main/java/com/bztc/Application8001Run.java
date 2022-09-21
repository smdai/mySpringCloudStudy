package com.bztc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.bztc.mapper"})
public class Application8001Run {
    public static void main(String[] args) {
        SpringApplication.run(Application8001Run.class, args);
    }
}
