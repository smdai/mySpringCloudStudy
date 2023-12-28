package com.bztc.web.rest;

import cn.hutool.json.JSONUtil;
import com.bztc.dto.ResultDto;
import com.bztc.service.CodeLibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author daism
 * @create 2022-09-25 19:43
 * @description 网站地址查询resource层
 */
@RestController
@RequestMapping("/api/websitequeryresource")
@Slf4j
public class WebsiteQueryResource {
    @Autowired
    private CodeLibraryService codeLibraryService;

    /**
     * 描述：根据类型查询网站
     *
     * @return com.bztc.entity.WebsiteList
     * @author daism
     * @date 2022-09-25 20:32:44
     */
    @GetMapping("/querywebsitesrc")
    public ResultDto<String> queryWebsiteSrc(@RequestParam("websiteType") String websiteType) {
        log.info("根据类型查询网站入参:{}", JSONUtil.toJsonStr(websiteType));
        return new ResultDto<>(codeLibraryService.queryValueByCode("WebsiteRecord", websiteType));
    }
}
