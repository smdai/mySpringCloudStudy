package com.bztc.service.impl;

import cn.hutool.core.lang.UUID;
import com.bztc.constant.RedisConstants;
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
}
