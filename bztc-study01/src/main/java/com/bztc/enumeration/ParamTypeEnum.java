package com.bztc.enumeration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author daism
 * @create 2022-10-14 17:30
 * @description 参数类型
 */
public enum ParamTypeEnum {
    /**
     * 字符串
     */
    STRING(String.class),
    /**
     * 整型
     */
    INT(BigDecimal.class),
    /**
     * 数值
     */
    NUMBER(BigDecimal.class),
    /**
     * 布尔
     */
    BOOLEAN(Boolean.class),
    /**
     * 列表
     */
    LIST(List.class),
    /**
     * 映射
     */
    MAP(Map.class),
    /**
     * 日期
     */
    DATE(LocalDate.class),
    ;
    private final Class<?> clazz;

    ParamTypeEnum(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
