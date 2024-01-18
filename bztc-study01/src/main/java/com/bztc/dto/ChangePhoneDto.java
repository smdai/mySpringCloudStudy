package com.bztc.dto;

import lombok.Data;

/**
 * @author daism
 * @create 2024-01-18 17:46
 * @description 修改手机号
 */
@Data
public class ChangePhoneDto {
    private String phone;
    private String phoneCode;
}
