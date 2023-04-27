package com.bztc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bztc.domain.UserInfo;
import com.bztc.enumeration.LoginEnum;

/**
 * @author daishuming
 * @description 针对表【user_info(用户信息表)】的数据库操作Service
 * @createDate 2022-10-14 16:45:30
 */
public interface UserInfoService extends IService<UserInfo> {
    /**
     * 描述：用户登录
     *
     * @param userInfo 用户信息
     * @return {@link LoginEnum}
     * @author daism
     * @date 2022-10-14 17:22:28
     */
    int login(UserInfo userInfo);
}
