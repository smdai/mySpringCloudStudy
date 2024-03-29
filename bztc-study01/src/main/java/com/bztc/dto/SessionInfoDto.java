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
     * 是否有编辑权限，true-有，false-没有
     */
    private boolean editAuth;
    /**
     * 菜单权限列表
     */
    private List<String> menuAuthList;
    /**
     * 查询展示菜单列表
     */
    private List<MenuInfoDto> menuInfoDtos;
    /**
     * 登录令牌
     */
    private String token;
    /**
     * 用户id
     */
    private Integer userId;
}
