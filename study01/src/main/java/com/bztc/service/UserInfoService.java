package com.bztc.service;

import com.bztc.domain.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author daishuming
* @description 针对表【user_info(用户信息表)】的数据库操作Service
* @createDate 2022-10-14 16:45:30
*/
public interface UserInfoService extends IService<UserInfo> {
    int login(UserInfo userInfo);
}
