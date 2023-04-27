package com.bztc.service;

/**
 * session相关方法
 */
public interface SessionService {
    /**
     * 获取token
     *
     * @param userId 用户id
     * @return token
     */
    String getToken(String userId);
}
