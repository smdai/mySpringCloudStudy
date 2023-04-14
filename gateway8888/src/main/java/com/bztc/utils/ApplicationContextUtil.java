package com.bztc.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author daism
 * @create 2023-03-27 17:46
 * @description 上下文工具
 */
@Component
public final class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    /**
     * 根据名称、class获取bean对象
     *
     * @param name
     * @param requiredType
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> requiredType) {
        return context.getBean(name, requiredType);
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
