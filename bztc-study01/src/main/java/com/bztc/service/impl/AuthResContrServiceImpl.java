package com.bztc.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.constant.Constants;
import com.bztc.constant.RedisConstants;
import com.bztc.domain.*;
import com.bztc.dto.*;
import com.bztc.mapper.AuthResContrMapper;
import com.bztc.mapper.MenuInfoMapper;
import com.bztc.service.*;
import com.bztc.utils.RedisUtil;
import com.bztc.utils.RequestHeaderUtil;
import com.bztc.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    private MenuInfoService menuInfoService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private MenuInfoMapper menuInfoMapper;
    @Autowired
    private AuthResContrMapper authResContrMapper;
    @Autowired
    private ControlInfoService controlInfoService;

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
        sessionInfoDto.setRoleList(userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList()));
        //查询权限
        QueryWrapper<AuthResContr> editAuthResContrQueryWrapper = new QueryWrapper<>();
        editAuthResContrQueryWrapper
                .eq("res_object_type", Constants.RES_OBJECT_TYPE_R)
                .in("res_object_id", userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList()))
                .eq("status", Constants.STATUS_EFFECT);
        List<AuthResContr> authResContrs = this.list(editAuthResContrQueryWrapper);
        //查询菜单
        QueryWrapper<MenuInfo> menuInfoQueryWrapper = new QueryWrapper<>();
        menuInfoQueryWrapper.eq("status", Constants.STATUS_EFFECT)
                .in("menu_id", authResContrs.stream().filter(it -> Constants.RES_CONTR_TYPE_M.equals(it.getResContrType())).map(AuthResContr::getResContrId).collect(Collectors.toList()))
                .orderByAsc("sort_no");
        List<MenuInfo> menuInfos = menuInfoService.list(menuInfoQueryWrapper);
        List<MenuInfoDto> menuInfoDtos = menuInfoService.changeMenu(menuInfos);
        sessionInfoDto.setMenuInfoDtos(menuInfoDtos);
        sessionInfoDto.setMenuAuthList(menuInfos.stream().map(MenuInfo::getRouteName).collect(Collectors.toList()));
        //查询控制点
        List<Integer> controlIds = authResContrs.stream().filter(it -> Constants.RES_CONTR_TYPE_C.equals(it.getResContrType())).map(AuthResContr::getResContrId).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(controlIds)) {
            QueryWrapper<ControlInfo> controlInfoQueryWrapper = new QueryWrapper<>();
            controlInfoQueryWrapper.eq("status", Constants.STATUS_EFFECT)
                    .in("control_id", authResContrs.stream().filter(it -> Constants.RES_CONTR_TYPE_C.equals(it.getResContrType())).map(AuthResContr::getResContrId).collect(Collectors.toList()));
            List<ControlInfo> controlInfoList = controlInfoService.list(controlInfoQueryWrapper);
            if (CollectionUtil.isNotEmpty(controlInfoList)) {
                List<ControlInfoDto> controlInfoDtos = controlInfoList.stream().map(it -> {
                    ControlInfoDto controlInfoDto = new ControlInfoDto();
                    controlInfoDto.setControlId(it.getControlId());
                    controlInfoDto.setControlKey(it.getControlKey());
                    controlInfoDto.setControlUrl(it.getControlUrl());
                    return controlInfoDto;
                }).collect(Collectors.toList());
                sessionInfoDto.setControlInfoDtos(controlInfoDtos);
            }
        }
        //获取token
        sessionService.setSessionInfo(userId.toString(), sessionInfoDto);
        return new ResultDto<>(sessionInfoDto);
    }

    /**
     * 根据roleid查询所关联的资源
     *
     * @param roleId
     * @return
     */
    @Override
    public ResultDto<List<AuthSourceDto>> queryAuthResContrByRoleId(String roleId) {
        QueryWrapper<AuthResContr> authResContrQueryWrapper = new QueryWrapper<>();
        authResContrQueryWrapper.eq("res_object_type", Constants.RES_OBJECT_TYPE_R)
                .eq("res_object_id", roleId)
                .eq("status", Constants.STATUS_EFFECT);
        List<AuthResContr> authResContrs = authResContrMapper.selectList(authResContrQueryWrapper);
        List<AuthSourceDto> authSourceDtoList = authResContrs.stream().map(it -> {
            AuthSourceDto authSourceDto = new AuthSourceDto();
            if (Constants.RES_CONTR_TYPE_M.equals(it.getResContrType())) {
                MenuInfo menuInfo = menuInfoMapper.selectById(it.getResContrId());
                authSourceDto.setObjectId(Constants.RES_CONTR_TYPE_M + menuInfo.getMenuId());
                authSourceDto.setSourceId(menuInfo.getMenuId());
                authSourceDto.setSourceName(menuInfo.getMenuName());
                authSourceDto.setSourceType(Constants.RES_CONTR_TYPE_M);
                authSourceDto.setLabel(menuInfo.getMenuName());
            } else if (Constants.RES_CONTR_TYPE_C.equals(it.getResContrType())) {
                ControlInfo controlInfo = controlInfoService.getBaseMapper().selectById(it.getResContrId());
                authSourceDto.setObjectId(Constants.RES_CONTR_TYPE_C + controlInfo.getControlId());
                authSourceDto.setSourceId(controlInfo.getControlId());
                authSourceDto.setSourceName(controlInfo.getControlName());
                authSourceDto.setSourceType(Constants.RES_CONTR_TYPE_C);
                authSourceDto.setLabel(controlInfo.getControlName());
            }
            return authSourceDto;
        }).collect(Collectors.toList());
        return new ResultDto<>(authSourceDtoList);
    }

    /**
     * 查询所有资源
     *
     * @return
     */
    @Override
    public ResultDto<List<AuthSourceDto>> queryAllAuthResContr() {
        return new ResultDto<>(queryAllAuthResContrList());
    }

    /**
     * 查询所有资源
     *
     * @return
     */
    private List<AuthSourceDto> queryAllAuthResContrList() {
        List<AuthSourceDto> list;
        //查询所有菜单
        QueryWrapper<MenuInfo> menuInfoQueryWrapper = new QueryWrapper<>();
        menuInfoQueryWrapper.eq("status", Constants.STATUS_EFFECT);
        menuInfoQueryWrapper.orderByAsc("sort_no");
        List<MenuInfo> menuInfos = menuInfoMapper.selectList(menuInfoQueryWrapper);
        List<MenuInfo> levelOneMenus = menuInfos.stream().filter(it -> it.getMenuLevel().equals(Constants.MENU_ONE_LEVEL)).collect(Collectors.toList());
        List<MenuInfo> levelTwoMenus = menuInfos.stream().filter(it -> it.getMenuLevel().equals(Constants.MENU_TWO_LEVEL)).collect(Collectors.toList());
        List<MenuInfo> levelThreeMenus = menuInfos.stream().filter(it -> it.getMenuLevel().equals(Constants.MENU_THREE_LEVEL)).collect(Collectors.toList());
        list = levelOneMenus.stream().map(one -> {
            AuthSourceDto oneAuthSourceDto = new AuthSourceDto();
            oneAuthSourceDto.setSourceType(Constants.RES_CONTR_TYPE_M);
            oneAuthSourceDto.setObjectId(Constants.RES_CONTR_TYPE_M + one.getMenuId());
            oneAuthSourceDto.setSourceId(one.getMenuId());
//            oneAuthSourceDto.setAuthFlag(false);
            oneAuthSourceDto.setSourceName(one.getMenuName());
            oneAuthSourceDto.setLabel((one.getMenuName()));
            //查询是否有下一级菜单
            List<MenuInfo> twoMenus = levelTwoMenus.stream().filter(two -> two.getUpMenuId().equals(one.getMenuId())).collect(Collectors.toList());
            List<AuthSourceDto> oneChildren;
            if (CollectionUtil.isNotEmpty(twoMenus)) {
                oneChildren = twoMenus.stream().map(two -> {
                    AuthSourceDto twoAuthSourceDto = new AuthSourceDto();
                    twoAuthSourceDto.setSourceType(Constants.RES_CONTR_TYPE_M);
                    twoAuthSourceDto.setObjectId(Constants.RES_CONTR_TYPE_M + two.getMenuId());
                    twoAuthSourceDto.setSourceId(two.getMenuId());
//                    twoAuthSourceDto.setAuthFlag(false);
                    twoAuthSourceDto.setSourceName(two.getMenuName());
                    twoAuthSourceDto.setLabel(two.getMenuName());
                    //查询是否有下一级菜单
                    List<MenuInfo> threeMenus = levelThreeMenus.stream().filter(three -> three.getUpMenuId().equals(two.getMenuId())).collect(Collectors.toList());
                    List<AuthSourceDto> twoChildren;
                    if (CollectionUtil.isNotEmpty(threeMenus)) {
                        twoChildren = threeMenus.stream().map(three -> {
                            AuthSourceDto threeAuthSourceDto = new AuthSourceDto();
                            threeAuthSourceDto.setSourceType(Constants.RES_CONTR_TYPE_M);
                            threeAuthSourceDto.setSourceId(three.getMenuId());
                            threeAuthSourceDto.setObjectId(Constants.RES_CONTR_TYPE_M + three.getMenuId());
                            threeAuthSourceDto.setSourceName(three.getMenuName());
                            threeAuthSourceDto.setLabel(three.getMenuName());
                            //查询控制点
                            List<AuthSourceDto> threeChildren = controlInfoService.queryControlByMenuId(three.getMenuId());
                            threeAuthSourceDto.setChildren(threeChildren);
                            return threeAuthSourceDto;
                        }).collect(Collectors.toList());
                    } else {
                        twoChildren = controlInfoService.queryControlByMenuId(two.getMenuId());
                    }
                    twoAuthSourceDto.setChildren(twoChildren);
                    return twoAuthSourceDto;
                }).collect(Collectors.toList());
            } else {
                oneChildren = controlInfoService.queryControlByMenuId(one.getMenuId());
            }
            oneAuthSourceDto.setChildren(oneChildren);
            return oneAuthSourceDto;
        }).collect(Collectors.toList());
        return list;
    }

    /**
     * 保存用户关联权限
     *
     * @param authSourceDtos
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultDto<Integer> save(List<AuthSourceDto> authSourceDtos, Integer roleId) {
        //过滤出最后一层级的资源
        List<AuthSourceDto> filterAuthSourceDtos = authSourceDtos.stream().filter(it -> CollectionUtil.isEmpty(it.getChildren())).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(filterAuthSourceDtos)) {
            return new ResultDto<>(0);
        }
        Set<String> objectIdSet = new HashSet<>();
        findParentAuthResContr(objectIdSet, authSourceDtos);
        log.info("objectIdSet>>>>>>{}", JSONUtil.toJsonStr(objectIdSet));
        //先删除角色关联的所有权限
        this.deleteByRoleId(roleId);
        //再关联新的权限
        List<AuthResContr> authResContrs = objectIdSet.stream().map(it -> {
            AuthResContr authResContr = new AuthResContr();
            authResContr.setResObjectType(Constants.RES_OBJECT_TYPE_R);
            authResContr.setResObjectId(roleId);
            authResContr.setResContrType(it.substring(0, 1));
            authResContr.setResContrId(Integer.parseInt(it.substring(1)));
            authResContr.setStatus(Constants.STATUS_EFFECT);
            authResContr.setInputUser(UserUtil.getUserId());
            authResContr.setUpdateUser(UserUtil.getUserId());
            return authResContr;
        }).collect(Collectors.toList());
        this.saveBatch(authResContrs);
        return new ResultDto<>(0);
    }

    /**
     * 根据角色号删除权限
     *
     * @param roleId
     * @return
     */
    @Override
    public int deleteByRoleId(int roleId) {
        QueryWrapper<AuthResContr> authResContrQueryWrapper = new QueryWrapper<>();
        authResContrQueryWrapper.eq("res_object_type", Constants.RES_OBJECT_TYPE_R)
                .eq("res_object_id", roleId)
                .in("res_contr_type", Arrays.asList(Constants.RES_CONTR_TYPE_M, Constants.RES_CONTR_TYPE_C));
        return this.authResContrMapper.delete(authResContrQueryWrapper);
    }

    /**
     * 根据控制点id删除
     *
     * @param controlId
     * @return
     */
    @Override
    public int deleteByControlId(int controlId) {
        QueryWrapper<AuthResContr> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("res_contr_id", controlId).eq("res_contr_type", Constants.RES_CONTR_TYPE_C);
        return this.authResContrMapper.delete(queryWrapper);
    }

    /**
     * 根据菜单id删除
     *
     * @param menuId
     * @return
     */
    @Override
    public int deleteByMenuId(int menuId) {
        QueryWrapper<AuthResContr> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("res_contr_id", menuId).eq("res_contr_type", Constants.RES_CONTR_TYPE_M);
        return this.authResContrMapper.delete(queryWrapper);
    }

    /**
     * 查询上级菜单
     *
     * @param objectIdSet
     * @param list
     */
    private void findParentAuthResContr(Set<String> objectIdSet, List<AuthSourceDto> list) {
        List<String> mAuthSourceObjectIdList = list.stream().filter(it -> it.getSourceType().equals(Constants.RES_CONTR_TYPE_M)).map(AuthSourceDto::getObjectId).collect(Collectors.toList());

        List<String> cAuthSourceObjectIdList = list.stream().filter(it -> it.getSourceType().equals(Constants.RES_CONTR_TYPE_C)).map(AuthSourceDto::getObjectId).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(cAuthSourceObjectIdList)) {
            objectIdSet.addAll(cAuthSourceObjectIdList);
            //查询控制点关联的菜单，并返回菜单集合
            QueryWrapper<ControlInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("status", Constants.STATUS_EFFECT).in("control_id", cAuthSourceObjectIdList.stream().map(it -> it.substring(1)).collect(Collectors.toList()));
            List<ControlInfo> controlInfoList = controlInfoService.list(queryWrapper);
            List<String> menus = controlInfoList.stream().map(it -> Constants.RES_CONTR_TYPE_M + it.getMenuId()).collect(Collectors.toList());
            mAuthSourceObjectIdList.addAll(menus);
        }

        if (CollectionUtil.isNotEmpty(mAuthSourceObjectIdList)) {
            objectIdSet.addAll(mAuthSourceObjectIdList);
            List<MenuInfo> menuInfos = menuInfoMapper.selectBatchIds(mAuthSourceObjectIdList.stream().map(it -> it.substring(1)).collect(Collectors.toList()));
            List<MenuInfo> haveUpMenuInfos = menuInfos.stream().filter(it -> Objects.nonNull(it.getUpMenuId())).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(haveUpMenuInfos)) {
                List<AuthSourceDto> upAuthSourceDtos = haveUpMenuInfos.stream().map(it -> {
                    AuthSourceDto authSourceDto = new AuthSourceDto();
                    authSourceDto.setSourceId(it.getUpMenuId());
                    authSourceDto.setSourceType(Constants.RES_CONTR_TYPE_M);
                    authSourceDto.setObjectId(Constants.RES_CONTR_TYPE_M + it.getUpMenuId());
                    return authSourceDto;
                }).collect(Collectors.toList());
                findParentAuthResContr(objectIdSet, upAuthSourceDtos);
            }
        }
    }

}




