package com.bztc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bztc.domain.MenuInfo;
import com.bztc.dto.MenuInfoDto;

import java.util.List;

/**
* @author daishuming
* @description 针对表【menu_info(菜单信息表)】的数据库操作Service
* @createDate 2022-10-17 17:07:28
*/
public interface MenuInfoService extends IService<MenuInfo> {
    List<MenuInfoDto> queryMenu(String userName);
}
