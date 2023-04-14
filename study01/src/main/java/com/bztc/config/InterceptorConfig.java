package com.bztc.config;

import com.bztc.interceptor.CustomizeAccessInterceptor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author daism
 * @create 2023-04-13 16:12
 * @description 拦截器配置
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
        addV1Rule(registry);
    }

    private void addV1Rule(InterceptorRegistry registry) {
        //注册自己的拦截器并设置拦截的请求路径
        registry.addInterceptor(new CustomizeAccessInterceptor()).addPathPatterns("/**");  //拦截所有请求
//        registry.addInterceptor(new CustomizeAccessInterceptor()).addPathPatterns("/student/getStudentName");  //拦截student相关请求
    }
}
