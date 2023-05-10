package com.bztc.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author daism
 * @create 2023-05-09 18:31
 * @description chatgpt
 */
@Data
public class ChatGptDto implements Serializable {
    private static final long serialVersionUID = -8255477446156809997L;
    private String message;
}
