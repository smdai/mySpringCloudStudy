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
    /**
     * 描述：查询菜单
     *
     * @param userName 用户名称
     * @return java.util.List<com.bztc.domain.MenuInfo>
     * @author daism
     * @date 2022-10-17 17:34:43
     */
    List<MenuInfoDto> queryMenu(String userName);

    /**
     * 描述：菜单分层转化
     *
     * @param menuInfos 菜单列表
     * @return java.util.List<com.bztc.dto.MenuInfoDto>
     * @author daism
     * @date 2022-10-17 17:52:09
     */
    List<MenuInfoDto> changeMenu(List<MenuInfo> menuInfos);
}
