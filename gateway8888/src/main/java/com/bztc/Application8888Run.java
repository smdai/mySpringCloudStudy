package com.bztc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author daism
 * @create 2023-04-14 10:40
 * @description 启动
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Application8888Run {
    public static void main(String[] args) {
        SpringApplication.run(Application8888Run.class, args);
    }
}
