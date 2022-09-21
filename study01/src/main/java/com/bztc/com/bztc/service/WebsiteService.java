package com.bztc.com.bztc.service;

import com.bztc.entity.WebsiteList;
import com.bztc.mapper.WebsiteListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author daism
 * @create 2022-09-21 15:55
 * @description 网站列表service层
 */
@Service
public class WebsiteService {
    @Autowired
    private WebsiteListMapper websiteListMapper;
    /**
     * 描述： 根据主键查询网址
     * @author daism
     * @date 2022-09-21 16:07:04
     * @param id 主键
     * @return com.bztc.entity.WebsiteList
     */
    public WebsiteList selectById(long id){
        return websiteListMapper.selectById(id);
    }
}
