package com.bztc.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author daism
 * @create 2023-04-06 11:35
 * @description es客户端配置
 */
@Configuration
@Slf4j
public class RestHighLevelClientConfig {
    @Value("${elasticsearch.host}")
    private String elasticsearchHost;
    @Value("${elasticsearch.port}")
    private String port;

    @Bean
    public RestHighLevelClient getClient() {
        log.info("elasticsearchHost:{},prot:{}", elasticsearchHost, port);
        return new RestHighLevelClient(RestClient.builder(HttpHost.create(elasticsearchHost + ":" + port)));
    }
}
