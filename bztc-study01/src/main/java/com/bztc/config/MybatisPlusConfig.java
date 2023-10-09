package com.bztc.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author daism
 * @create 2023-10-09 10:26
 * @description mybatisplus配置
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * map类型key转驼峰
     *
     * @return {@link ConfigurationCustomizer}
     */
    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> configuration.setObjectWrapperFactory(new MybatisMapWrapperFactory());
    }

}
