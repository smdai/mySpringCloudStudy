package com.bztc.web.rest;

import com.bztc.dto.MenuInfoDto;
import com.bztc.dto.ResultDto;
import com.bztc.service.MenuInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author daism
 * @create 2022-10-17 15:35
 * @description 菜单管理controller层
 */
@RestController
@RequestMapping("/api/menuInfoResource")
public class MenuInfoResource {
    private static final Logger logger = LoggerFactory.getLogger(MenuInfoResource.class);
    @Autowired
    private MenuInfoService menuInfoService;

    /*
     * 描述：查询菜单
     * @author daism
     * @date 2022-10-17 17:14:33
     * @param userName
     * @return com.bztc.dto.ResultDto<java.lang.String>
     */
    @GetMapping("/querymenu")
    public ResultDto<List<MenuInfoDto>> queryMenu(@RequestParam("userName") String userName){
        logger.info("登录查询菜单入参：{}",userName);
        return new ResultDto<>(menuInfoService.queryMenu(userName));
    }
}
