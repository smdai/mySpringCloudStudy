package com.bztc.enumeration;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author daism
 * @create 2022-10-14 17:30
 * @description 用户登录枚举
 */
public enum LoginEnum {
    /**
     * 登录成功
     */
    SUCCESS(200, "登录成功"),
    /**
     * 用户不存在
     */
    NON_ERROR(300, "用户不存在"),
    /**
     * 用户已停用
     */
    NO_AVAIL_ERROR(400, "用户已停用"),
    /**
     * 用户名或密码不正确
     */
    WRONG_ERROR(500, "用户名或密码不正确"),
    ;

    public static final Map<Integer, String> KEY_VALUE = new HashMap<>();

    static {
        for (LoginEnum enumData : EnumSet.allOf(LoginEnum.class)) {
            KEY_VALUE.put(enumData.key, enumData.value);
        }
    }

    public final Integer key;
    public final String value;

    private LoginEnum(int key, String value) {
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
