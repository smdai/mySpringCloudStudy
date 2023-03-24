package com.bztc.dto;

import lombok.Data;

import java.util.List;

/**
 * @author daism
 * @create 2022-10-17 17:46
 * @description 菜单信息
 */
@Data
public class MenuInfoDto {
    /**
     * 菜单id
     */
    private Integer menuId;
    /**
     * 菜单等级
     */
    private Integer menuLevel;
    /**
     * 上级菜单id
     */
    private Integer upMenuId;

    /**
     * 菜单URL
     */
    private String menuUrl;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * icon
     */
    private String icon;
    /**
     * 路由名称
     */
    private String routeName;

    /**
     * 菜单类型
     */
    private String menuType;

    private List<MenuInfoDto> subs;
}
