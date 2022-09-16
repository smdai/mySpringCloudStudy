package com.bztc.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端调后段测试类
 * @author daism
 * @date 2022-09-16 11:20:17
 */
@RestController
@RequestMapping("/test")
public class Test {
    /**
     * 描述：
     *
     * @return java.lang.String
     * @author daism
     * @date 2022-09-16 11:20:17
     */
    @GetMapping("/testGetMapping")
    public String testGetMapping() {
        return "success";
    }
}

