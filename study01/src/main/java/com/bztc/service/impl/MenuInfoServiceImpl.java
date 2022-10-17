package com.bztc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.constant.Constants;
import com.bztc.domain.MenuInfo;
import com.bztc.dto.MenuInfoDto;
import com.bztc.mapper.MenuInfoMapper;
import com.bztc.service.MenuInfoService;
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

    /*
     * 描述：查询菜单
     * @author daism
     * @date 2022-10-17 17:34:43
     * @param userName
     * @return java.util.List<com.bztc.domain.MenuInfo>
     */
    @Override
    public List<MenuInfoDto> queryMenu(String userName) {
        QueryWrapper<MenuInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Constants.STATUS_EFFECT);
        List<MenuInfo> menuInfos = this.list(queryWrapper);
        return changeMenu(menuInfos);
    }

    /*
     * 描述：菜单分层转化
     * @author daism
     * @date 2022-10-17 17:52:09
     * @param menuInfos
     * @return java.util.List<com.bztc.dto.MenuInfoDto>
     */
    private List<MenuInfoDto> changeMenu(List<MenuInfo> menuInfos) {
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
}




