package com.bztc.enumeration;

import java.util.*;

/**
 * @author daism
 * @create 2022-10-14 17:30
 * @description 用户登录枚举
 */
public enum LoginEnum {
    SUCCESS(200, "登录成功"),
    NON_ERROR(300, "用户不存在"),
    NOAVAIL_ERROR(400, "用户已停用"),
    WRONG_ERROR(500, "用户名或密码不正确"),
    ;

    public static final Map<Integer, String> keyValue = new HashMap<>();
    public final Integer key;
    public final String value;

    private LoginEnum(int key, String value) {
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
        for (LoginEnum enumData : EnumSet.allOf(LoginEnum.class)) {
            keyValue.put(enumData.key, enumData.value);
        }
    }
}
