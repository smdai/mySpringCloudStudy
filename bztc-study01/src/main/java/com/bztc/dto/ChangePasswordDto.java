package com.bztc.dto;

import lombok.Data;

/**
 * @author daism
 * @create 2024-01-18 16:17
 * @description 修改密码
 */
@Data
public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;
    private String userId;
}
