package com.bztc.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息表
 *
 * @TableName user_info
 */
@Data
public class UserInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像url
     */
    private String avatarUrl;

}