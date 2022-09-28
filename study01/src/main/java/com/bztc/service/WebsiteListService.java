package com.bztc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bztc.entity.WebsiteList;

/**
* @author daishuming
* @description 针对表【website_list(网站列表)】的数据库操作Service
* @createDate 2022-09-28 10:07:53
*/
public interface WebsiteListService extends IService<WebsiteList> {
    WebsiteList getInfoById(Integer id);
}
