package com.bztc.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.constant.Constants;
import com.bztc.constant.RedisConstants;
import com.bztc.domain.AuthResContr;
import com.bztc.domain.MenuInfo;
import com.bztc.domain.UserInfo;
import com.bztc.domain.UserRole;
import com.bztc.dto.MenuInfoDto;
import com.bztc.dto.ResultDto;
import com.bztc.dto.SessionInfoDto;
import com.bztc.mapper.AuthResContrMapper;
import com.bztc.service.*;
import com.bztc.utils.RedisUtil;
import com.bztc.utils.RequestHeaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author daishuming
 * @description 针对表【auth_res_contr(权限关联表)】的数据库操作Service实现
 * @createDate 2022-10-18 09:25:40
 */
@Service
@Slf4j
public class AuthResContrServiceImpl extends ServiceImpl<AuthResContrMapper, AuthResContr>
        implements AuthResContrService {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private AuthResContrService authResContrService;
    @Autowired
    private MenuInfoService menuInfoService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SessionService sessionService;

    /**
     * 描述：获取session
     *
     * @return com.bztc.dto.ResultDto<com.bztc.dto.SessionInfoDto>
     * @author daism
     * @date 2022-10-18 18:45:55
     */
    @Override
    public ResultDto<SessionInfoDto> getSession() {
        SessionInfoDto sessionInfoDto = new SessionInfoDto();
        //获取userId
        Object userId = redisUtil.get(RedisConstants.SESSION_USERID_KEY + ":" + RequestHeaderUtil.getToken());
        if (Objects.isNull(userId)) {
            log.error("token 不正确。");
            return null;
        }
        //查询用户信息
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("status", Constants.STATUS_EFFECT)
                .eq("id", userId.toString());
        UserInfo userInfo = userInfoService.getOne(userInfoQueryWrapper);
        if (Objects.isNull(userInfo)) {
            return null;
        }
        //查询用户角色
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("status", Constants.STATUS_EFFECT)
                .eq("user_id", userInfo.getId())
                .select("distinct role_id");
        List<UserRole> userRoles = userRoleService.list(userRoleQueryWrapper);
        if (CollectionUtil.isEmpty(userRoles)) {
            return null;
        }
        //查询编辑权限
        QueryWrapper<AuthResContr> editAuthResContrQueryWrapper = new QueryWrapper<>();
        editAuthResContrQueryWrapper
                .eq("res_object_type", Constants.RES_OBJECT_TYPE_R)
                .in("res_object_id", userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList()))
//                .eq("res_contr_type",Constants.RES_CONTR_TYPE_E)
//                .eq("res_contr_id",Constants.STATUS_EFFECT)
                .eq("status", Constants.STATUS_EFFECT);
        List<AuthResContr> authResContrs = authResContrService.list(editAuthResContrQueryWrapper);
        sessionInfoDto.setEditAuth(!CollectionUtil.isEmpty(authResContrs.stream().filter(it ->
                Constants.RES_CONTR_TYPE_E.equals(it.getResContrType()) && it.getResContrId() == 1).collect(Collectors.toList())));
        //查询菜单权限
        QueryWrapper<AuthResContr> menuAuthResContrQueryWrapper = new QueryWrapper<>();
        menuAuthResContrQueryWrapper.select("distinct res_contr_id")
                .eq("res_object_type", Constants.RES_OBJECT_TYPE_R)
                .in("res_object_id", userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList()))
                .eq("res_contr_type", Constants.RES_CONTR_TYPE_M)
                .eq("status", Constants.STATUS_EFFECT);
        List<AuthResContr> menuAuthResContrs = authResContrService.list(menuAuthResContrQueryWrapper);
        //查询菜单
        QueryWrapper<MenuInfo> menuInfoQueryWrapper = new QueryWrapper<>();
        menuInfoQueryWrapper.eq("status", Constants.STATUS_EFFECT)
                .in("menu_id", authResContrs.stream().filter(it -> Constants.RES_CONTR_TYPE_M.equals(it.getResContrType())).map(AuthResContr::getResContrId).collect(Collectors.toList()))
                .orderByAsc("sort_no");
        List<MenuInfo> menuInfos = menuInfoService.list(menuInfoQueryWrapper);
        List<MenuInfoDto> menuInfoDtos = menuInfoService.changeMenu(menuInfos);
        sessionInfoDto.setMenuInfoDtos(menuInfoDtos);

        sessionInfoDto.setMenuAuthList(menuInfos.stream().map(MenuInfo::getRouteName).collect(Collectors.toList()));
        //获取token
//        sessionInfoDto.setToken(sessionService.getToken(String.valueOf(userInfo.getId())));
//        sessionInfoDto.setUserId(userInfo.getId());
        sessionService.setSessionInfo(userId.toString(), sessionInfoDto);
        return new ResultDto<>(sessionInfoDto);
    }
}




