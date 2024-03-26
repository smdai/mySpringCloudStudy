package com.bztc.enumeration;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author daism
 * @create 2022-10-14 17:30
 * @description 申请类型
 */
public enum ObjectTypeEnum {
    /**
     * 权限申请
     */
    AUTH_TYPE("AuthType", "权限申请"),
    ;

    public static final Map<String, String> KEY_VALUE = new HashMap<>();

    static {
        for (ObjectTypeEnum enumData : EnumSet.allOf(ObjectTypeEnum.class)) {
            KEY_VALUE.put(enumData.key, enumData.value);
        }
    }

    public final String key;
    public final String value;

    private ObjectTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String lookup(String key) {
        return KEY_VALUE.get(key);
    }

    public final String value() {
        return KEY_VALUE.get(this.key);
    }
}
