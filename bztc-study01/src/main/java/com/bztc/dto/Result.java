package com.bztc.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * @author daism
 * @create 2023-07-11 10:03
 * @description 参数结果封装
 */
public final class Result<T> implements Serializable {
    private static final long serialVersionUID = -208009274606675267L;
    /**
     * 异常
     */
    private Throwable exception;
    /**
     * 数据
     */
    private T data;
    /**
     * 请求
     */
    private Map<String, Object> request;
    /**
     * 类型只用来校验，并不参与计算，设置为瞬态属性便于序列化
     */
    private transient Class<T> type;

    private Result() {

    }

    private Result(T data, Class<T> type) {
        this.data = data;
        this.type = type;
    }

    public static Result emptyResult() {
        return new Result();
    }

    public static <T> Result<T> newResult(T data, Class<T> type) {
        return new Result<>(data, type);
    }

    public boolean isException() {
        return this.exception != null;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Object> getRequest() {
        return request;
    }

    public void setRequest(Map<String, Object> request) {
        this.request = request;
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }
}
