package com.bztc.web.rest;

import com.bztc.entity.WebsiteList;
import com.bztc.service.IWebsiteListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author daism
 * @create 2022-09-25 19:43
 * @description 网站地址resource层
 */
@RestController
@RequestMapping("/websiteListResource")
public class WebsiteListResource {
    private static final Logger logger = LoggerFactory.getLogger(WebsiteListResource.class);
    @Autowired
    private IWebsiteListService iWebsiteListService;

    /*
     * 描述：根据主键id查询网站信息
     * @author daism
     * @date 2022-09-25 20:32:44
     * @param s
     * @return com.bztc.entity.WebsiteList
     */
    @GetMapping("/getInfoById/{s}")
    public WebsiteList getInfoById(@PathVariable Integer s){
        logger.info("------->/testGetFromDataBase");
        return iWebsiteListService.getInfoById(s);
    }
}
