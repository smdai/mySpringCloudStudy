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
    SUCCESS(200, "成功"),
    WRONG_ERROR(999, "失败"),
    ;

    public static final Map<Integer, String> keyValue = new HashMap<>();
    public final Integer key;
    public final String value;

    private WebStatusEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String lookup(Integer key) {
        return keyValue.get(key);
    }

    public final String value() {
        return keyValue.get(this.key);
    }

    static {
        for (WebStatusEnum enumData : EnumSet.allOf(WebStatusEnum.class)) {
            keyValue.put(enumData.key, enumData.value);
        }
    }
}
