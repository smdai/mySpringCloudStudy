package com.bztc.dto;

import lombok.Data;

/**
 * @author daism
 * @create 2022-09-28 14:48
 * @description 响应模型
 */
@Data
public class ResultDto<T> {
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 总页数
     */
    private Long total;
    /**
     * 返回数据
     */
    private T data;

    public ResultDto() {

    }

    public ResultDto(T data) {
        this.code = 200;
        this.message = "sucess";
        this.data = data;
    }

    public ResultDto(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultDto(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultDto(Long total, T data) {
        this.code = 200;
        this.message = "success";
        this.data = data;
        this.total = total;
    }
}
