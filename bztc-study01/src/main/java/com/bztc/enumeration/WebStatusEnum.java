package com.bztc.enumeration;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author daism
 * @create 2022-10-14 17:30
 * @description 前后端交互枚举
 */
public enum WebStatusEnum {
    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    /**
     * 失败
     */
    WRONG_ERROR(999, "失败"),
    ;

    public static final Map<Integer, String> KEY_VALUE = new HashMap<>();

    static {
        for (WebStatusEnum enumData : EnumSet.allOf(WebStatusEnum.class)) {
            KEY_VALUE.put(enumData.key, enumData.value);
        }
    }

    public final Integer key;
    public final String value;

    private WebStatusEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String lookup(Integer key) {
        return KEY_VALUE.get(key);
    }

    public final String value() {
        return KEY_VALUE.get(this.key);
    }
}
