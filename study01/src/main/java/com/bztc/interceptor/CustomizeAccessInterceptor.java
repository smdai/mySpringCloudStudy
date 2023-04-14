package com.bztc.interceptor;

import com.bztc.constant.RedisConstants;
import com.bztc.utils.ApplicationContextUtil;
import com.bztc.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author daism
 * @create 2023-04-13 16:06
 * @description 自定义拦截前端请求
 */
@Component
@Slf4j
public class CustomizeAccessInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        String authorization = request.getHeader("authorization");
        String userId = request.getHeader("userId");
        String requestURI = request.getRequestURI();
        if (StringUtils.isBlank(requestURI)) {
            response.setStatus(404);
            response.setHeader("errorMsg", "no requestURI");
            log.error("no requestURI");
            return false;
        }
        if (!requestURI.endsWith("/login") && !requestURI.endsWith("/getsession")) {
            String token = authorization.substring("Bearer ".length());
            if (StringUtils.isBlank(token) || StringUtils.isBlank(userId)) {
                response.setStatus(401);
                response.setHeader("errorMsg", "no token or no userId");
                log.error("no token or no userId,[{}],[{}]", token, userId);
                return false;
            }
            //从redis中获取token
            RedisUtil redisUtil = ApplicationContextUtil.getBean("redisUtil", RedisUtil.class);
            Object o = redisUtil.get(RedisConstants.SESSION_TOKEN_KEY + ":" + userId);
            if (Objects.isNull(o)) {
                response.setStatus(401);
                response.setHeader("errorMsg", "redis token non");
                log.error("userid[{}],redis token non", userId);
                return false;
            }
            String redisToken = (String) o;
            if (!redisToken.equals(token)) {
                response.setStatus(401);
                response.setHeader("errorMsg", "token inconformity");
                log.error("userid[{}],token inconformity", userId);
                return false;
            }
            //初始化缓存时间
            redisUtil.expire(RedisConstants.SESSION_TOKEN_KEY + ":" + userId, RedisConstants.SESSION_TOKEN_TTL_SECONDS);
        }
        return true;
    }
}
