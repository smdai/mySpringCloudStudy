package com.bztc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.constant.Constants;
import com.bztc.domain.AuthResContr;
import com.bztc.domain.MenuInfo;
import com.bztc.domain.UserRole;
import com.bztc.dto.MenuInfoDto;
import com.bztc.dto.MenuTreeDto;
import com.bztc.mapper.MenuInfoMapper;
import com.bztc.service.AuthResContrService;
import com.bztc.service.MenuInfoService;
import com.bztc.service.UserInfoService;
import com.bztc.service.UserRoleService;
import com.bztc.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author daishuming
 * @description 针对表【menu_info(菜单信息表)】的数据库操作Service实现
 * @createDate 2022-10-17 17:07:28
 */
@Service
public class MenuInfoServiceImpl extends ServiceImpl<MenuInfoMapper, MenuInfo> implements MenuInfoService {
    private static final Logger logger = LoggerFactory.getLogger(MenuInfoServiceImpl.class);
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private AuthResContrService authResContrService;

    /**
     * 描述：查询菜单
     *
     * @return java.util.List<com.bztc.domain.MenuInfo>
     * @author daism
     * @date 2022-10-17 17:34:43
     */
    @Override
    public List<MenuInfoDto> queryMenu() {
        //查询用户角色
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("status", Constants.STATUS_EFFECT)
                .eq("user_id", UserUtil.getUserId())
                .select("distinct role_id");
        List<UserRole> userRoles = userRoleService.list(userRoleQueryWrapper);
        if (CollectionUtil.isEmpty(userRoles)) {
            return null;
        }
        //查询权限关联
        QueryWrapper<AuthResContr> authResContrQueryWrapper = new QueryWrapper<>();
        authResContrQueryWrapper.select("distinct res_contr_id")
                .eq("res_object_type", Constants.RES_OBJECT_TYPE_R)
                .in("res_object_id", userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList()))
                .eq("res_contr_type", Constants.RES_CONTR_TYPE_M)
                .eq("status", Constants.STATUS_EFFECT);
        List<AuthResContr> authResContrs = authResContrService.list(authResContrQueryWrapper);
        //查询菜单
        QueryWrapper<MenuInfo> menuInfoQueryWrapper = new QueryWrapper<>();
        menuInfoQueryWrapper.eq("status", Constants.STATUS_EFFECT)
                .in("menu_id", authResContrs.stream().map(AuthResContr::getResContrId).collect(Collectors.toList()))
                .orderByAsc("sort_no");
        List<MenuInfo> menuInfos = this.list(menuInfoQueryWrapper);
        return changeMenu(menuInfos);
    }

    /**
     * 描述：菜单分层转化
     *
     * @return java.util.List<com.bztc.dto.MenuInfoDto>
     * @author daism
     * @date 2022-10-17 17:52:09
     */
    @Override
    public List<MenuInfoDto> changeMenu(List<MenuInfo> menuInfos) {
        List<MenuInfoDto> threeLevelList = menuInfos.stream().filter(it -> Constants.MENU_THREE_LEVEL == it.getMenuLevel())
                .map(it -> {
                    MenuInfoDto menuInfoDto = new MenuInfoDto();
                    BeanUtil.copyProperties(it, menuInfoDto);
                    return menuInfoDto;
                }).collect(Collectors.toList());

        List<MenuInfoDto> twoLevelList = menuInfos.stream().filter(it -> Constants.MENU_TWO_LEVEL == it.getMenuLevel())
                .map(it -> {
                    MenuInfoDto menuInfoDto = new MenuInfoDto();
                    BeanUtil.copyProperties(it, menuInfoDto);
                    return menuInfoDto;
                }).collect(Collectors.toList());

        for (MenuInfoDto twoLevelMenuInfo : twoLevelList) {
            List<MenuInfoDto> list = new ArrayList<>();
            for (MenuInfoDto threeLevelMenuInfo : threeLevelList) {
                if (Objects.equals(threeLevelMenuInfo.getUpMenuId(), twoLevelMenuInfo.getMenuId())) {
                    list.add(threeLevelMenuInfo);
                }
            }
            if (CollectionUtil.isNotEmpty(list)) {
                twoLevelMenuInfo.setSubs(list);
            }
        }

        List<MenuInfoDto> oneLevelList = menuInfos.stream().filter(it -> Constants.MENU_ONE_LEVEL == it.getMenuLevel())
                .map(it -> {
                    MenuInfoDto menuInfoDto = new MenuInfoDto();
                    BeanUtil.copyProperties(it, menuInfoDto);
                    return menuInfoDto;
                }).collect(Collectors.toList());

        for (MenuInfoDto oneLevelMenuInfo : oneLevelList) {
            List<MenuInfoDto> list = new ArrayList<>();
            for (MenuInfoDto twoLevelMenuInfo : twoLevelList) {
                if (Objects.equals(twoLevelMenuInfo.getUpMenuId(), oneLevelMenuInfo.getMenuId())) {
                    list.add(twoLevelMenuInfo);
                }
            }
            if (CollectionUtil.isNotEmpty(list)) {
                oneLevelMenuInfo.setSubs(list);
            }
        }

        return oneLevelList;
    }

    /**
     * 查询下一层级菜单
     *
     * @param menuId
     * @return
     */
    @Override
    public List<MenuInfo> queryNextMenu(int menuId) {
        QueryWrapper<MenuInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("up_menu_id", menuId);
        queryWrapper.orderByAsc("sort_no");
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询菜单树
     *
     * @return
     */
    @Override
    public List<MenuTreeDto> queryAllMenuTree() {
        List<MenuTreeDto> list;
        //查询所有菜单
        QueryWrapper<MenuInfo> menuInfoQueryWrapper = new QueryWrapper<>();
        menuInfoQueryWrapper.eq("status", Constants.STATUS_EFFECT);
        menuInfoQueryWrapper.orderByAsc("sort_no");
        List<MenuInfo> menuInfos = this.baseMapper.selectList(menuInfoQueryWrapper);
        List<MenuInfo> levelOneMenus = menuInfos.stream().filter(it -> it.getMenuLevel().equals(Constants.MENU_ONE_LEVEL)).collect(Collectors.toList());
        List<MenuInfo> levelTwoMenus = menuInfos.stream().filter(it -> it.getMenuLevel().equals(Constants.MENU_TWO_LEVEL)).collect(Collectors.toList());
        List<MenuInfo> levelThreeMenus = menuInfos.stream().filter(it -> it.getMenuLevel().equals(Constants.MENU_THREE_LEVEL)).collect(Collectors.toList());
        list = levelOneMenus.stream().map(one -> {
            MenuTreeDto oneMenuTreeDto = new MenuTreeDto();
            oneMenuTreeDto.setMenuId(one.getMenuId());
            oneMenuTreeDto.setMenuName(one.getMenuName());
            oneMenuTreeDto.setLabel((one.getMenuName()));
            oneMenuTreeDto.setLevel(Constants.MENU_ONE_LEVEL);
            //查询是否有下一级菜单
            List<MenuInfo> twoMenus = levelTwoMenus.stream().filter(two -> two.getUpMenuId().equals(one.getMenuId())).collect(Collectors.toList());
            List<MenuTreeDto> oneChildren;
            if (CollectionUtil.isNotEmpty(twoMenus)) {
                oneChildren = twoMenus.stream().map(two -> {
                    MenuTreeDto twoMenuTreeDto = new MenuTreeDto();
                    twoMenuTreeDto.setMenuId(two.getMenuId());
                    twoMenuTreeDto.setMenuName(two.getMenuName());
                    twoMenuTreeDto.setLabel(two.getMenuName());
                    twoMenuTreeDto.setLevel(Constants.MENU_TWO_LEVEL);
                    //查询是否有下一级菜单
                    List<MenuInfo> threeMenus = levelThreeMenus.stream().filter(three -> three.getUpMenuId().equals(two.getMenuId())).collect(Collectors.toList());
                    List<MenuTreeDto> twoChildren;
                    if (CollectionUtil.isNotEmpty(threeMenus)) {
                        twoChildren = threeMenus.stream().map(three -> {
                            MenuTreeDto threeMenuTreeDto = new MenuTreeDto();
                            threeMenuTreeDto.setMenuId(three.getMenuId());
                            threeMenuTreeDto.setMenuName(three.getMenuName());
                            threeMenuTreeDto.setLabel(three.getMenuName());
                            threeMenuTreeDto.setLevel(Constants.MENU_THREE_LEVEL);
                            return threeMenuTreeDto;
                        }).collect(Collectors.toList());
                    } else {
                        twoChildren = new ArrayList<>();
                    }
                    twoMenuTreeDto.setChildren(twoChildren);
                    return twoMenuTreeDto;
                }).collect(Collectors.toList());
            } else {
                oneChildren = new ArrayList<>();
            }
            oneMenuTreeDto.setChildren(oneChildren);
            return oneMenuTreeDto;
        }).collect(Collectors.toList());
        return list;
    }

}




