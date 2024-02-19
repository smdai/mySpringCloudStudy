package com.bztc.filters;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.bztc.constants.RedisConstants;
import com.bztc.dto.ControlInfoDto;
import com.bztc.dto.SessionInfoDto;
import com.bztc.utils.ApplicationContextUtil;
import com.bztc.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author daism
 * @create 2023-04-14 17:20
 * @description 权限过滤
 * //顺序注解：指定过滤器的顺序。（也可以通过Ordered接口实现）
 */
@Order(-1)
@Component
@RefreshScope
public class AuthorizeFilter implements GlobalFilter, Ordered {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizeFilter.class);
    private static final int AUTHORIZATION_CHECK_LENGTH = 8;
    private static final String START_WHITE_URL = "/api/file/";
    @Value("${white.urls}")
    private String whiteUrls;
    @Value("${bztc.redis.custom.ttl:3600}")
    private int customTtl;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            //1.获取请求参数
            ServerHttpRequest request = exchange.getRequest();
            //2.获取url
            String url = request.getPath().value();
            url = url.substring(url.indexOf("/api"));
            if (url.startsWith(START_WHITE_URL)) {
                return chain.filter(exchange);
            }
            //3.获取请求头
            HttpHeaders headers = request.getHeaders();
            //4.获取token
            String authorization = headers.getFirst("authorization");
            //5.获取userId
            String userId = headers.getFirst("userId");
            logger.info("requestUrl:{},userId:{},authorization:{}", url, userId, authorization);
            logger.info("whiteUrls:{}", whiteUrls);
            List<String> whiteUrlList = new ArrayList<>();
            if (StringUtils.isNotBlank(whiteUrls)) {
                whiteUrlList = Arrays.asList(whiteUrls.split(","));
            }
            if (!whiteUrlList.contains(url)) {
                if (StringUtils.isBlank(authorization) || authorization.length() < AUTHORIZATION_CHECK_LENGTH || StringUtils.isBlank(userId)) {
                    logger.error("authorization or userId is null");
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
                String token = authorization.substring("Bearer ".length());
                if (StringUtils.isBlank(token)) {
                    logger.error("no token,[{}],[{}]", token, userId);
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
                //从redis中获取token
                RedisUtil redisUtil = ApplicationContextUtil.getBean("redisUtil", RedisUtil.class);
                Object o = redisUtil.get(RedisConstants.SESSION_TOKEN_KEY + ":" + userId);
                if (Objects.isNull(o)) {
                    logger.error("userid[{}],redis token non", userId);
                    exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
                    return exchange.getResponse().setComplete();
                }
                String redisToken = (String) o;
                if (!redisToken.equals(token)) {
                    logger.error("userid[{}],token unconformity", userId);
                    exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
                    return exchange.getResponse().setComplete();
                }
                //从redis中获取控制点
                Object sessionObject = redisUtil.get(RedisConstants.SESSION_AUTH_CONTR_KEY + ":" + userId);
                if (Objects.isNull(sessionObject)) {
                    logger.error("userid[{}],session null fail", userId);
                    exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
                    return exchange.getResponse().setComplete();
                }
                SessionInfoDto sessionInfoDto = JSONUtil.toBean(sessionObject.toString(), SessionInfoDto.class);
                List<ControlInfoDto> controlInfoDtos = sessionInfoDto.getControlInfoDtos();
                if (CollectionUtil.isEmpty(controlInfoDtos)) {
                    logger.error("userid[{}],controll null fail", userId);
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
                String finalUrl = url;
                boolean match = controlInfoDtos.stream().anyMatch(it -> it.getControlUrl().equals(finalUrl));
                if (!match) {
                    logger.error("userid[{}],controll access fail", userId);
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
                //初始化缓存时间
                redisUtil.expire(RedisConstants.SESSION_TOKEN_KEY + ":" + userId, customTtl);
                redisUtil.expire(RedisConstants.SESSION_USERID_KEY + ":" + token, customTtl);
                redisUtil.expire(RedisConstants.SESSION_AUTH_CONTR_KEY + ":" + userId, customTtl);
            }
            return chain.filter(exchange);
        } catch (Exception e) {
            logger.error("gateway全局过滤出错！", e);
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
