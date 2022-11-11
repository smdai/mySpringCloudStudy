package com.bztc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan({"com.bztc.mapper"})
@EnableDiscoveryClient
public class Application8001Run {
    public static void main(String[] args) {
        SpringApplication.run(Application8001Run.class, args);
    }
}
