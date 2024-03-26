package com.bztc.enumeration;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author daism
 * @create 2022-10-14 17:30
 * @description 流程阶段
 */
public enum FlowPhaseEnum {
    /**
     * 用户申请
     */
    APPLY("1000", "申请(待提交)"),
    APPROVE("2000", "审批中"),
    BACK("3000", "退回"),
    AGREE("8000", "批准"),
    REJECT("9000", "拒绝"),
    ;

    public static final Map<String, String> KEY_VALUE = new HashMap<>();

    static {
        for (FlowPhaseEnum enumData : EnumSet.allOf(FlowPhaseEnum.class)) {
            KEY_VALUE.put(enumData.key, enumData.value);
        }
    }

    public final String key;
    public final String value;

    private FlowPhaseEnum(String key, String value) {
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
