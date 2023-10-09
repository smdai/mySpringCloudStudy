package com.bztc.handler;

/**
 * @author daism
 * @create 2023-10-09 11:27
 * @description 全局异常处理
 */

import com.bztc.dto.R;
import com.bztc.dto.ResultDto;
import com.bztc.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 请求方式不支持
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResultDto handleException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return R.fail("not support' " + e.getMethod() + "'req");
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultDto notFount(RuntimeException e) {
        log.error("server errror:", e);
        return R.fail("server errror:" + e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultDto handleException(Exception e) {
        log.error(e.getMessage(), e);
        return R.fail("server errror");
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultDto businessException(ApiException e) {
        log.error("api exception {} {}", e.getCode(), e.getMessage());
        return R.build(e.getCode(), e.getMessage());
    }

    /**
     * 参数异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultDto argumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException exception {}", e.getMessage());
        return R.fail(e.getMessage());
    }
}

