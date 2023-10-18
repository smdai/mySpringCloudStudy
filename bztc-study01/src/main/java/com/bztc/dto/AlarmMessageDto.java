package com.bztc.dto;

import lombok.Data;

import java.util.List;

/**
 * @author daism
 * @create 2022-09-25 20:57
 * @description skyWalking告警dto类
 */
@Data
public class AlarmMessageDto {
    private int scopeId;
    private String scope;
    private String name;
    private String id0;
    private String id1;
    private String ruleName;
    private String alarmMessage;
    private List<Tag> tags;
    private long startTime;
    private transient int period;
    private transient boolean onlyAsCondition;

    @Data
    public static class Tag {
        private String key;
        private String value;
    }
}
