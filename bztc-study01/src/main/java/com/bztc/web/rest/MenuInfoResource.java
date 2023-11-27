package com.bztc.web.rest;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bztc.constant.Constants;
import com.bztc.domain.MenuInfo;
import com.bztc.dto.MenuInfoDto;
import com.bztc.dto.MenuTreeDto;
import com.bztc.dto.ResultDto;
import com.bztc.service.AuthResContrService;
import com.bztc.service.MenuInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author daism
 * @create 2022-10-17 15:35
 * @description 菜单管理controller层
 */
@RestController
@RequestMapping("/api/menuInfoResource")
public class MenuInfoResource {
    private static final Logger logger = LoggerFactory.getLogger(MenuInfoResource.class);
    @Autowired
    private MenuInfoService menuInfoService;
    @Autowired
    private AuthResContrService authResContrService;

    /**
     * 描述：查询菜单
     *
     * @return com.bztc.dto.ResultDto<java.lang.String>
     * @author daism
     * @date 2022-10-17 17:14:33
     */
    @GetMapping("/querymenu")
    public ResultDto<List<MenuInfoDto>> queryMenu() {
        logger.info("登录查询菜单");
        return new ResultDto<>(menuInfoService.queryMenu());
    }

    /**
     * 查询菜单列表
     *
     * @return 菜单
     */
    @GetMapping("/querybypage")
    public ResultDto<List<MenuInfo>> querybypage(@RequestParam("param") String params) {
        logger.info("分页查询入参：{}", params);
        JSONObject jsonObject = JSONUtil.parseObj(params);

        Page<MenuInfo> queryPage = new Page<>((int) jsonObject.get("pageIndex"), (int) jsonObject.get("pageSize"));

        QueryWrapper<MenuInfo> queryWrapper = new QueryWrapper<>();
        if (!StrUtil.isBlankIfStr(jsonObject.get("menuName"))) {
            queryWrapper.like("menu_name", jsonObject.get("menuName"));
        }
        if (!StrUtil.isBlankIfStr(jsonObject.get("componentUrl"))) {
            queryWrapper.like("component_url", jsonObject.get("componentUrl"));
        }
        if (!StrUtil.isBlankIfStr(jsonObject.get("menuLevel"))) {
            queryWrapper.like("menu_level", jsonObject.get("menuLevel"));
        }
        if (!StrUtil.isBlankIfStr(jsonObject.get("status"))) {
            queryWrapper.like("status", jsonObject.get("status"));
        }
        //1-生效，0-失效
        queryWrapper.orderByAsc("sort_no");
        Page<MenuInfo> websiteListPage = this.menuInfoService.page(queryPage, queryWrapper);
        return new ResultDto<>(websiteListPage.getTotal(), websiteListPage.getRecords());
    }

    /**
     * 描述：新增菜单
     *
     * @return com.bztc.dto.ResultDto<com.bztc.entity.MenuInfo>
     * @author daism
     * @date 2022-09-29 10:35:10
     */
    @PostMapping("/insert")
    public ResultDto<MenuInfo> insert(@RequestBody MenuInfo menuInfo) {
        logger.info("新增入参：{}", JSONUtil.toJsonStr(menuInfo));
        ResultDto<MenuInfo> resultDto = new ResultDto<>();
        menuInfo.setStatus(Constants.STATUS_EFFECT);
        menuInfo.setInputUser(Constants.ADMIN);
        menuInfo.setUpdateUser(Constants.ADMIN);
        try {
            this.menuInfoService.save(menuInfo);
            resultDto.setCode(200);
            resultDto.setData(menuInfo);
        } catch (Exception e) {
            logger.error("插入数据库失败！", e);
            resultDto.setCode(400);
            resultDto.setMessage("插入数据库失败," + e.getMessage());
        }
        return resultDto;
    }

    /**
     * 描述：编辑-更新
     *
     * @return com.bztc.dto.ResultDto<com.bztc.entity.MenuInfo>
     * @author daism
     * @date 2022-10-14 09:55:17
     */
    @PostMapping("/update")
    public ResultDto<MenuInfo> update(@RequestBody MenuInfo menuInfo) {
        logger.info("更新入参：{}", JSONUtil.toJsonStr(menuInfo));
        ResultDto<MenuInfo> resultDto = new ResultDto<>();
        menuInfo.setUpdateUser(Constants.ADMIN);
        try {
            this.menuInfoService.updateById(menuInfo);
            resultDto.setCode(200);
            resultDto.setData(menuInfo);
        } catch (Exception e) {
            logger.error("更新数据库失败！", e);
            resultDto.setCode(400);
            resultDto.setMessage("更新数据库失败," + e.getMessage());
        }
        return resultDto;
    }

    /**
     * 描述：根据主键删除
     *
     * @return com.bztc.dto.ResultDto<java.lang.Integer>
     * @author daism
     * @date 2022-10-14 10:05:22
     */
    @PostMapping("/delete")
    public ResultDto<Integer> delete(@RequestBody MenuInfo menuInfo) {
        logger.info("删除入参：{}", JSONUtil.toJsonStr(menuInfo));
        ResultDto<Integer> resultDto = new ResultDto<>();
        try {
            if (Constants.STATUS_EFFECT.equals(menuInfo.getStatus())) {
                menuInfo.setUpdateUser(Constants.ADMIN);
                menuInfo.setStatus(Constants.STATUS_NOAVAIL);
                UpdateWrapper<MenuInfo> wrapper = new UpdateWrapper<>();
                wrapper.eq("menu_id", menuInfo.getMenuId());
                wrapper.set("status", Constants.STATUS_NOAVAIL);
                this.menuInfoService.update(wrapper);
            } else {
                this.menuInfoService.removeById(menuInfo);
                this.authResContrService.deleteByMenuId(menuInfo.getMenuId());
            }
            resultDto.setCode(200);
        } catch (Exception e) {
            logger.error("删除数据库失败！", e);
            resultDto.setCode(400);
            resultDto.setMessage("删除数据库失败," + e.getMessage());
        }
        return resultDto;
    }

    /**
     * 查询菜单树
     *
     * @return
     */
    @GetMapping("/queryallmenutree")
    public ResultDto<List<MenuTreeDto>> queryAllMenuTree() {
        return new ResultDto<>(menuInfoService.queryAllMenuTree());
    }
}
