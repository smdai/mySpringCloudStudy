package com.bztc.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author daism
 * @create 2023-03-27 17:46
 * @description 上下文工具
 */
@Component
public final class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext context;
    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 根据名称、class获取bean对象
     * @param name
     * @param requiredType
     * @return
     * @param <T>
     */
    public static <T> T getBean(String name,Class<T> requiredType){
        return context.getBean(name,requiredType);
    }
}
