package com.bztc.dto;

import lombok.Data;

/**
 * @author daism
 * @create 2022-10-17 17:46
 * @description 控制点信息
 */
@Data
public class ControlInfoDto {
    /**
     * 控制点id
     */
    private Integer controlId;
    /**
     * 控制点键
     */
    private String controlKey;
    /**
     * 控制点url
     */
    private String controlUrl;
}
