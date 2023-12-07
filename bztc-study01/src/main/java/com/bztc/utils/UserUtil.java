package com.bztc.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.bztc.constant.Constants;
import com.bztc.constant.RedisConstants;
import com.bztc.dto.SessionInfoDto;
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

    /**
     * 是否需要数据权限，true-需要，false-不需要
     *
     * @return true-需要，false-不需要
     */
    public static boolean judgeAuth() {
        RedisUtil redisUtil = ApplicationContextUtil.getBean("redisUtil", RedisUtil.class);
        SessionInfoDto sessionInfoDto = redisUtil.getBean(RedisConstants.SESSION_AUTH_CONTR_KEY + ":" + getUserId(), SessionInfoDto.class);
        if (Objects.isNull(sessionInfoDto) || CollectionUtil.isEmpty(sessionInfoDto.getRoleList())) {
            return true;
        }
        return !sessionInfoDto.getRoleList().contains(Constants.ADMIN_ROLE_ID);
    }
}
