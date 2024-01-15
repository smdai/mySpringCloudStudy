package com.bztc.enumeration;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author daism
 * @create 2022-10-14 17:30
 * @description 上传文件业务类型
 */
public enum FileBusinessTypeEnum {
    /**
     * 图片记录
     */
    IMAGE_RECORD("001", "图片记录"),
    ;

    public static final Map<String, String> KEY_VALUE = new HashMap<>();

    static {
        for (FileBusinessTypeEnum enumData : EnumSet.allOf(FileBusinessTypeEnum.class)) {
            KEY_VALUE.put(enumData.key, enumData.value);
        }
    }

    public final String key;
    public final String value;

    private FileBusinessTypeEnum(String key, String value) {
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
