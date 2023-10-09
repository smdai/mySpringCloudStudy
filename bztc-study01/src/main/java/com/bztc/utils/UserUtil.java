package com.bztc.utils;

import com.bztc.constant.RedisConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author daism
 * @create 2023-10-09 09:27
 * @description 用户信息工具类
 */
@Slf4j
public class UserUtil {
    /**
     * 获取userId
     *
     * @return userId
     */
    public static String getUserId() {
        RedisUtil redisUtil = ApplicationContextUtil.getBean("redisUtil", RedisUtil.class);
        Object userId = redisUtil.get(RedisConstants.SESSION_USERID_KEY + ":" + RequestHeaderUtil.getToken());
        if (Objects.isNull(userId)) {
            log.error("token 不正确。");
            return null;
        }
        return String.valueOf(userId);
    }
}
