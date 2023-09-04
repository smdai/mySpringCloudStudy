package com.bztc.constant;

/**
 * @author daism
 * @create 2022-10-14 09:47
 * @description 常量类
 */
public class Constants {
    /**
     * 状态-有效
     **/
    public static final String STATUS_EFFECT = "1";
    /**
     * 状态-无效
     **/
    public static final String STATUS_NOAVAIL = "0";

    /**
     * 用户-管理员
     **/
    public static final String ADMIN = "admin";

    /**
     * 菜单-第一层级
     **/
    public static final int MENU_ONE_LEVEL = 1;
    /**
     * 菜单-第二层级
     **/
    public static final int MENU_TWO_LEVEL = 2;
    /**
     * 菜单-第三层级
     **/
    public static final int MENU_THREE_LEVEL = 3;

    /**
     * 关联对象类型-角色
     **/
    public static final String RES_OBJECT_TYPE_R = "R";
    /**
     * 关联权限类型-菜单
     **/
    public static final String RES_CONTR_TYPE_M = "M";
    /**
     * 关联权限类型-全局编辑
     **/
    public static final String RES_CONTR_TYPE_E = "E";
    /**
     * rabbitmq 方法类型-已创建好的queue
     */
    public static final String RABBIT_MQ_METHOD_TYPE = "created";
}
