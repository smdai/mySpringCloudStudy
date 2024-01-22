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
    public static final String ADMIN = "1";

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
     * 关联权限类型-控制点
     **/
    public static final String RES_CONTR_TYPE_C = "C";
    /**
     * rabbitmq 方法类型-已创建好的queue
     */
    public static final String RABBIT_MQ_METHOD_TYPE = "created";
    /**
     * 超级管理员角色id
     */
    public static final Integer ADMIN_ROLE_ID = 1;
    /**
     * 普通角色id
     */
    public static final Integer COMMON_ROLE_ID = 2;

    /**
     * 发送状态：1-成功
     */
    public static final String SEND_STATUS_1 = "1";
    /**
     * 发送状态：2-失败
     */
    public static final String SEND_STATUS_2 = "2";
    /**
     * 文件路径前缀
     */
    public static final String IMAGE_PREFIX = "/bztc-study01/api/file";

}
