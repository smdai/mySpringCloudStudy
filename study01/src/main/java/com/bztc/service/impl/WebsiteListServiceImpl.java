package com.bztc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.entity.WebsiteList;
import com.bztc.mapper.WebsiteListMapper;
import com.bztc.service.IWebsiteListService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 网站列表 服务实现类
 * </p>
 *
 * @author daism
 * @since 2022-09-21
 */
@Service
public class WebsiteListServiceImpl extends ServiceImpl<WebsiteListMapper, WebsiteList> implements IWebsiteListService {

}
