package com.bztc.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author daism
 * @create 2022-09-28 14:16
 * @description 在已经集成了Mybatis Plus的SpringBoot项目中加入如下分页拦截器的配置，让MybatisPlus支持分页
 */
@Configuration
public class MybatisPlusConfiguration {
    /**
     * 配置分页拦截器
     */
    @Bean
    public MybatisPlusInterceptor getMybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}
