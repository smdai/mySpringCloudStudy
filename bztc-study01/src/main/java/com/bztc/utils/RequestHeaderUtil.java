package com.bztc.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author daism
 * @create 2023-09-12 17:34
 * @description 获取请求头信息
 */
@Slf4j
public class RequestHeaderUtil {
    public static String getToken() {
        try {
            RequestAttributes reqAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) reqAttributes).getRequest();
            return request.getHeaders("authorization").nextElement().substring("Bearer ".length());
        } catch (Exception e) {
            log.error("获取请求头中的token出错。", e);
            return null;
        }
    }
}
