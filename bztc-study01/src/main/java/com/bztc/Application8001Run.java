package com.bztc;

import io.github.asleepyfish.annotation.EnableChatGPT;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 启动
 *
 * @author daishuming
 */
@SpringBootApplication
@MapperScan({"com.bztc.mapper"})
@EnableDiscoveryClient
@EnableCaching
@EnableChatGPT
public class Application8001Run {
    public static void main(String[] args) {
        SpringApplication.run(Application8001Run.class, args);
    }
}
