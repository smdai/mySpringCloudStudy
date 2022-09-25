package com.bztc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.entity.WebsiteList;
import com.bztc.mapper.WebsiteListMapper;
import com.bztc.service.IWebsiteListService;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
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
    /*
     * 描述：根据主键获取网站信息
     * @author daism
     * @date 2022-09-25 19:41:39
     * @param id
     * @return com.bztc.entity.WebsiteList
     */
    @Trace //将方法记录到skywalking中
    @Tags({@Tag(key = "getInfoById",value = "returnedObj"),
            @Tag(key = "getInfoById",value = "arg[0]")})//记录参数和返回值
    @Override
    public WebsiteList getInfoById(Integer id) {
        return this.getById(id);
    }
}
