package com.bztc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.constant.Constants;
import com.bztc.domain.UserInfo;
import com.bztc.enumeration.LoginEnum;
import com.bztc.mapper.UserInfoMapper;
import com.bztc.service.UserInfoService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author daishuming
 * @description 针对表【user_info(用户信息表)】的数据库操作Service实现
 * @createDate 2022-10-14 16:45:30
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    /**
     * 描述：用户登录
     *
     * @return int
     * @author daism
     * @date 2022-10-14 17:22:28
     */
    @Override
    public int login(UserInfo userInfoLogin) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userInfoLogin.getUserName());
        UserInfo userInfoData = this.getOne(queryWrapper);
        if (Objects.isNull(userInfoData) || !userInfoData.getPassword().equals(userInfoLogin.getPassword())) {
            return LoginEnum.WRONG_ERROR.key;
        }
        if (!Constants.STATUS_EFFECT.equals(userInfoData.getStatus())) {
            return LoginEnum.NO_AVAIL_ERROR.key;
        }
        return LoginEnum.SUCCESS.key;
    }
}




