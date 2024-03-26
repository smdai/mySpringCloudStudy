package com.bztc.enumeration;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author daism
 * @create 2022-10-14 17:30
 * @description 申请类型-流程号
 */
public enum ApplyTypeFlowEnum {
    /**
     * 权限申请
     */
    AUTH_APPLY("AuthApply", "FlowAuthApply"),
    ;

    public static final Map<String, String> APPLY_FLOW = new HashMap<>();

    static {
        for (ApplyTypeFlowEnum enumData : EnumSet.allOf(ApplyTypeFlowEnum.class)) {
            APPLY_FLOW.put(enumData.applyType, enumData.flowNo);
        }
    }

    public final String applyType;
    public final String flowNo;

    private ApplyTypeFlowEnum(String applyType, String flowNo) {
        this.applyType = applyType;
        this.flowNo = flowNo;
    }

    public static String lookup(String applyType) {
        return APPLY_FLOW.get(applyType);
    }

    public final String value() {
        return APPLY_FLOW.get(this.applyType);
    }
}
