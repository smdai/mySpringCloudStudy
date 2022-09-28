package com.bztc.web.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bztc.dto.QueryModel;
import com.bztc.dto.ResultDto;
import com.bztc.entity.WebsiteList;
import com.bztc.service.WebsiteListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author daism
 * @create 2022-09-25 19:43
 * @description 网站地址resource层
 */
@RestController
@RequestMapping("/api/websiteListResource")
public class WebsiteListResource {
    private static final Logger logger = LoggerFactory.getLogger(WebsiteListResource.class);
    @Autowired
    private WebsiteListService websiteListService;

    /*
     * 描述：根据主键id查询网站信息
     * @author daism
     * @date 2022-09-25 20:32:44
     * @param s
     * @return com.bztc.entity.WebsiteList
     */
    @GetMapping("/getInfoById/{s}")
    public WebsiteList getInfoById(@PathVariable Integer s) {
        logger.info("------->/testGetFromDataBase");
        return websiteListService.getInfoById(s);
    }

    /*
     * 描述：查询所有网站列表
     * @author daism
     * @date 2022-09-28 10:25:22
     * @param
     * @return java.util.List<com.bztc.entity.WebsiteList>
     */
    @GetMapping("/queryAll")
    public List<WebsiteList> queryAll() {
        return websiteListService.list();
    }

    /*
     * 描述：分页查询
     * @author daism
     * @date 2022-09-28 14:52:04
     * @param
     * @return java.util.List<com.bztc.entity.WebsiteList>
     */
    @GetMapping("/queryByPage")
    public ResultDto<List<WebsiteList>> queryByPageTest(QueryModel queryModel) {
        logger.info(queryModel.toString());
        Page<WebsiteList> queryPage = new Page<>(queryModel.getPageIndex(), queryModel.getPageSize());
        Page<WebsiteList> websiteListPage = websiteListService.page(queryPage);
        return new ResultDto<>(websiteListPage.getTotal(),websiteListPage.getRecords());
    }
}
