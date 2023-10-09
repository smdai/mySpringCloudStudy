package com.bztc.exception;

/**
 * @author daism
 * @create 2023-10-09 11:28
 * @description api exception
 */
public class ApiException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private int code;

    private String message;

    public ApiException(String message) {
        this.message = message;
    }

    public ApiException(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public ApiException(int code, String message) {
        this.message = message;
        this.code = code;
    }
//
//    public ApiException(IErrorCode errorCode){
//        this.code = errorCode.getCode();
//        this.message = errorCode.getMessage();
//    }

    public ApiException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}

