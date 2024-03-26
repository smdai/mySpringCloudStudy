package com.bztc.dto;

import lombok.Data;

/**
 * @author daism
 * @create 2024-03-22 16:38
 * @description 流程提交
 */
@Data
public class SubmitDto {
    private String objectType;
    private int objectNo;
    private String opinion;
    private String action;
}
