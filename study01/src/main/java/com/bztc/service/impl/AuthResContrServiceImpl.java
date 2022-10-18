package com.bztc.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.constant.Constants;
import com.bztc.domain.AuthResContr;
import com.bztc.domain.UserInfo;
import com.bztc.domain.UserRole;
import com.bztc.dto.ResultDto;
import com.bztc.dto.SessionInfoDto;
import com.bztc.service.AuthResContrService;
import com.bztc.mapper.AuthResContrMapper;
import com.bztc.service.UserInfoService;
import com.bztc.service.UserRoleService;
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
    implements AuthResContrService{
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private AuthResContrService authResContrService;
    /*
     * 描述：获取session
     * @author daism
     * @date 2022-10-18 18:45:55
     * @param userName
     * @return com.bztc.dto.ResultDto<com.bztc.dto.SessionInfoDto>
     */
    @Override
    public ResultDto<SessionInfoDto> getSession(String userName) {
        SessionInfoDto sessionInfoDto = new SessionInfoDto();
        //查询用户信息
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("status", Constants.STATUS_EFFECT)
                .eq("user_name",userName);
        UserInfo userInfo = userInfoService.getOne(userInfoQueryWrapper);
        if(Objects.isNull(userInfo)){
            return null;
        }
        //查询用户角色
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("status",Constants.STATUS_EFFECT)
                .eq("user_id",userInfo.getId())
                .select("distinct role_id");
        List<UserRole> userRoles = userRoleService.list(userRoleQueryWrapper);
        if(CollectionUtil.isEmpty(userRoles)){
            return null;
        }
        //查询权限关联
        QueryWrapper<AuthResContr> authResContrQueryWrapper = new QueryWrapper<>();
        authResContrQueryWrapper.select("distinct res_contr_id")
                .eq("res_object_type",Constants.RES_OBJECT_TYPE_R)
                .in("res_object_id",userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList()))
                .eq("res_contr_type",Constants.RES_CONTR_TYPE_E)
                .eq("res_contr_id",Constants.STATUS_EFFECT)
                .eq("status",Constants.STATUS_EFFECT);
        List<AuthResContr> authResContrs = authResContrService.list(authResContrQueryWrapper);
        if(CollectionUtil.isEmpty(authResContrs)){
            sessionInfoDto.setAuth(false);
        }else{
            sessionInfoDto.setAuth(true);
        }
        return new ResultDto<>(sessionInfoDto);
    }
}




