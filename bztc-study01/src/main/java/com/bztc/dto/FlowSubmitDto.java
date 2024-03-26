package com.bztc.dto;

import lombok.Data;

/**
 * @author daism
 * @create 2024-03-26 17:12
 * @description 流程提交
 */
@Data
public class FlowSubmitDto {
    private String objectType;
    private int objectNo;
    private String opinion;
    private int nextApproveUser;
}
