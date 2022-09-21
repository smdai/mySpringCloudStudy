package com.bztc.web.rest;

import com.bztc.entity.WebsiteList;
import com.bztc.service.IWebsiteListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端调后段测试类
 *
 * @author daism
 * @date 2022-09-16 11:20:17
 */
@RestController
@RequestMapping("/test")
public class Test {
    @Autowired
    private IWebsiteListService iWebsiteListService;
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
    @GetMapping("/testGetFromDataBase")
    public WebsiteList testGetFromDataBase(){
        return iWebsiteListService.getById(1);
    }
}

