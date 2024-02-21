package com.bztc.dto;

import lombok.Data;

/**
 * @author daism
 * @create 2024-02-21 09:08
 * @description 微信绑定
 */
@Data
public class WxBindDto {
    private String code;
    private String userName;
    private String password;
}
