package com.bztc.web.rest;

import com.bztc.domain.WebsiteList;
import com.bztc.service.WebsiteListService;
import com.bztc.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端调后段测试类
 *
 * @author daism
 * @date 2022-09-16 11:20:17
 */
@RestController
@RequestMapping("/bztcSystem/test")
@RefreshScope
@Slf4j
public class Test {
    @Autowired
    private WebsiteListService iWebsiteListService;
    @Autowired
    private RedisUtil redisUtil;

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;
    /**
     * 描述：测试get请求
     *
     * @return java.lang.String
     * @author daism
     * @date 2022-09-16 15:33:55
     */
    @GetMapping("/testGetMapping")
    public String testGetMapping() {
        return "success";
    }
    /**
     * 描述： 测试从数据库查询数据
     * @author daism
     * @date 2022-09-21 16:09:43
     * @return com.bztc.entity.WebsiteList
     */
    @GetMapping("/testGetFromDataBase/{s}")
    public WebsiteList testGetFromDataBase(@PathVariable int s){
        log.info("------->/testGetFromDataBase");
        return iWebsiteListService.getById(s);
    }
    /*
     * 描述：nacos配置测试
     * @author daism
     * @date 2022-11-11 10:13:34
     * @param
     * @return boolean
     */
    @RequestMapping("/testnacosconfig")
    public boolean get() {
        log.info("useLocalCache===={}",useLocalCache);
        return useLocalCache;
    }
    /*
     * 描述：测试redis
     * @author daism
     * @date 2022-11-11 17:50:22
     * @param
     * @return java.lang.String
     */
    @GetMapping("/testRedis")
    public String testRedis(){
        redisUtil.set("dai","shuming");
        return "success";
    }
    /*
     * 描述：测试redis
     * @author daism
     * @date 2022-11-11 17:50:34
     * @param
     * @return java.lang.String
     */
    @GetMapping("/testRedisGet")
    public String testRedisGet(){
        return (String)redisUtil.get("dai");
    }
}

