package com.bztc.dto;

import lombok.Data;

/**
 * @author daism
 * @create 2024-02-07 17:45
 * @description 微信登录dto
 */
@Data
public class Jscode2sessionDto {
    private String openid;
    private String session_key;
    private String unionid;
    private int errcode;
    private String errmsg;
}
