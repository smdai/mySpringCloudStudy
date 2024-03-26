package com.bztc.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.constant.Constants;
import com.bztc.constant.RedisConstants;
import com.bztc.domain.UserInfo;
import com.bztc.domain.UserRole;
import com.bztc.dto.SessionInfoDto;
import com.bztc.enumeration.LoginEnum;
import com.bztc.mapper.UserInfoMapper;
import com.bztc.service.SessionService;
import com.bztc.service.UserInfoService;
import com.bztc.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @Autowired
    RedisUtil redisUtil;

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
        sessionInfoDto.setUserName(userInfoData.getUserName());
        sessionInfoDto.setAvatarUrl(userInfoData.getAvatarUrl());
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

    /**
     * 根据userid获取userName
     *
     * @param userId
     * @return
     */
    @Override
    public String getUserNameByUserId(int userId) {
        if (userId <= 0) {
            return StrUtil.EMPTY;
        }
        Object o = redisUtil.get(RedisConstants.SYSTEM_USER_NAME_REL_ID);
        if (Objects.isNull(o) || StrUtil.isEmpty(((Map<Integer, String>) o).get(userId))) {
            //查询数据库
            UserInfo userInfo = this.baseMapper.selectById(userId);
            //刷新缓存
            refreshUserInfo();
            return Objects.isNull(userInfo) ? StringUtils.EMPTY : userInfo.getUserName();
        } else {
            return ((Map<Integer, String>) o).get(userId);
        }
    }

    /**
     * 查询用户
     *
     * @param queryPage
     * @param userRole
     * @return
     */
    @Override
    public Page<Map<String, Object>> selectUserByPage(Page<UserRole> queryPage, Map<String, Object> userRole) {
        return this.baseMapper.selectUserByPage(queryPage, userRole);
    }

    /**
     * 刷新缓存到redis
     */
    private void refreshUserInfo() {
        List<UserInfo> userInfos = this.baseMapper.selectList(new QueryWrapper<>());
        Map<Integer, String> map = new HashMap<>();
        userInfos.forEach(it -> {
            map.put(it.getId(), it.getUserName());
        });
        redisUtil.set(RedisConstants.SYSTEM_USER_NAME_REL_ID, map);
    }
}




