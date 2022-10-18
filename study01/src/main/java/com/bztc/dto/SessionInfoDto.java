package com.bztc.dto;

import lombok.Data;

/**
 * @author daism
 * @create 2022-10-18 18:34
 * @description session信息
 */
@Data
public class SessionInfoDto {
    /**
     * 是否有权限，true-有，false-没有
     */
    private boolean auth;
}
