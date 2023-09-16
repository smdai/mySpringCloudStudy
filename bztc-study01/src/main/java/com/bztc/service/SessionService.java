package com.bztc.service;

import com.bztc.dto.SessionInfoDto;

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

    /**
     * 设置token
     *
     * @param userId 用户号
     * @param token  token
     * @return userId
     */
    int setToken(int userId, String token);

    /**
     * 设置session
     *
     * @param userId         用户编号
     * @param sessionInfoDto session信息
     * @return session
     */
    SessionInfoDto setSessionInfo(String userId, SessionInfoDto sessionInfoDto);
}
