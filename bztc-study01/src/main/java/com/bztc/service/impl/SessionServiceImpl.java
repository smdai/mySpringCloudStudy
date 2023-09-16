package com.bztc.service.impl;

import cn.hutool.core.lang.UUID;
import com.bztc.constant.RedisConstants;
import com.bztc.dto.SessionInfoDto;
import com.bztc.service.SessionService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

/**
 * @author daism
 * @create 2023-04-13 11:46
 * @description session相关方法
 */
@Service
public class SessionServiceImpl implements SessionService {
    /**
     * 获取token
     */
    @Override
    @CachePut(value = RedisConstants.SESSION_TOKEN_KEY, key = "#userId")
    public String getToken(String userId) {
        return userId + "-" + UUID.randomUUID();
    }

    /**
     * 设置token
     *
     * @return userId
     */
    @Override
    @CachePut(value = RedisConstants.SESSION_USERID_KEY, key = "#token")
    public int setToken(int userId, String token) {
        return userId;
    }

    /**
     * 设置权限信息
     *
     * @param userId         用户编号
     * @param sessionInfoDto session信息
     * @return session信息
     */
    @Override
    @CachePut(value = RedisConstants.SESSION_AUTH_CONTR_KEY, key = "#userId")
    public SessionInfoDto setSessionInfo(String userId, SessionInfoDto sessionInfoDto) {
        return sessionInfoDto;
    }
}
