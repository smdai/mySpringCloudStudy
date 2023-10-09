package com.bztc.dto;

/**
 * @author daism
 * @create 2023-10-09 11:37
 * @description 返回
 */
public class R {
    public static ResultDto fail(String msg) {
        ResultDto<String> resultDto = new ResultDto<>();
        resultDto.setCode(999);
        resultDto.setMessage(msg);
        return resultDto;
    }

    public static ResultDto build(int code, String msg) {
        ResultDto<String> resultDto = new ResultDto<>();
        resultDto.setCode(code);
        resultDto.setMessage(msg);
        return resultDto;
    }
}
