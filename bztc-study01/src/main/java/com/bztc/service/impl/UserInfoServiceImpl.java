package com.bztc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.constant.Constants;
import com.bztc.domain.UserInfo;
import com.bztc.dto.SessionInfoDto;
import com.bztc.enumeration.LoginEnum;
import com.bztc.mapper.UserInfoMapper;
import com.bztc.service.SessionService;
import com.bztc.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author daishuming
 * @description 针对表【user_info(用户信息表)】的数据库操作Service实现
 * @createDate 2022-10-14 16:45:30
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Autowired
    SessionService sessionService;

    /**
     * 描述：用户登录
     *
     * @return int
     * @author daism
     * @date 2022-10-14 17:22:28
     */
    @Override
    public SessionInfoDto login(UserInfo userInfoLogin) {
        SessionInfoDto sessionInfoDto = new SessionInfoDto();
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userInfoLogin.getUserName());
        UserInfo userInfoData = this.getOne(queryWrapper);
        if (Objects.isNull(userInfoData) || !userInfoData.getPassword().equals(userInfoLogin.getPassword())) {
            sessionInfoDto.setLoginStatus(LoginEnum.WRONG_ERROR.key);
            return sessionInfoDto;
        }
        if (!Constants.STATUS_EFFECT.equals(userInfoData.getStatus())) {
            sessionInfoDto.setLoginStatus(LoginEnum.NO_AVAIL_ERROR.key);
            return sessionInfoDto;
        }
        String token = sessionService.getToken(String.valueOf(userInfoData.getId()));
        int i = sessionService.setToken(userInfoData.getId(), token);
        sessionInfoDto.setToken(token);
        sessionInfoDto.setUserId(userInfoData.getId());
        sessionInfoDto.setLoginStatus(LoginEnum.SUCCESS.key);

        return sessionInfoDto;
    }

    /**
     * 查询未关联角色的用户
     *
     * @param rowPage
     * @param roleId
     * @return
     */
    @Override
    public Page<UserInfo> queryUserNoRoles(Page<UserInfo> rowPage, String roleId) {
        return this.baseMapper.queryUserNoRoles(rowPage, roleId);
    }
}




