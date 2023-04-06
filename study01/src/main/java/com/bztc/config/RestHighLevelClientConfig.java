package com.bztc.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author daism
 * @create 2023-04-06 11:35
 * @description es客户端配置
 */
@Configuration
public class RestHighLevelClientConfig {
    @Bean
    public RestHighLevelClient getClient(){
        return new RestHighLevelClient(RestClient.builder(HttpHost.create("http://42.192.53.124:9200")));
    }
}
