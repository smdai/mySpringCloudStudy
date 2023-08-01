package com.bztc.dto;

import lombok.Data;

/**
 * @author daism
 * @create 2023-07-05 16:28
 * @description groovyTest
 */
@Data
public class GroovyTestDto {
    /**
     * groovy脚本
     */
    private String groovyScript;
    /**
     * json报文
     */
    private String jsonMsg;
}
