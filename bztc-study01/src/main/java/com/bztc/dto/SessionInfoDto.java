package com.bztc.dto;

import lombok.Data;

import java.util.List;

/**
 * @author daism
 * @create 2022-10-18 18:34
 * @description session信息
 */
@Data
public class SessionInfoDto {
    /**
     * 控制点权限列表
     */
    private List<ControlInfoDto> controlInfoDtos;
    /**
     * 菜单权限列表
     */
    private List<String> menuAuthList;
    /**
     * 查询展示菜单列表
     */
    private List<MenuInfoDto> menuInfoDtos;
    /**
     * 角色列表
     */
    private List<Integer> roleList;
    /**
     * 登录令牌
     */
    private String token;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 登录状态
     */
    private int loginStatus;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 头像url
     */
    private String avatarUrl;
}
