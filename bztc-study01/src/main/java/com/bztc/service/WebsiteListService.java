package com.bztc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bztc.domain.WebsiteList;

/**
 * @author daishuming
 * @description 针对表【website_list(网站列表)】的数据库操作Service
 * @createDate 2022-09-28 10:07:53
 */
public interface WebsiteListService extends IService<WebsiteList> {
    /**
     * 描述：根据主键获取网站信息
     *
     * @param id 主键id
     * @return com.bztc.entity.WebsiteList
     * @author daism
     * @date 2022-09-25 19:41:39
     */
    WebsiteList getInfoById(Integer id);
}
