package com.bztc.web.rest;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bztc.constant.Constants;
import com.bztc.domain.UserInfo;
import com.bztc.dto.ResultDto;
import com.bztc.dto.UserInfoDto;
import com.bztc.service.UserInfoService;
import com.bztc.service.UserRoleService;
import com.bztc.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author daism
 * @create 2022-10-17 15:35
 * @description 用户管理controller层
 */
@RestController
@RequestMapping("/api/userinforesource")
public class UserInfoResource {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoResource.class);
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 查询用户列表
     *
     * @return 用户
     */
    @GetMapping("/querybypage")
    public ResultDto<List<UserInfo>> querybypage(@RequestParam("param") String params) {
        logger.info("分页查询入参：{}", params);
        JSONObject jsonObject = JSONUtil.parseObj(params);

        Page<UserInfo> queryPage = new Page<>((int) jsonObject.get("pageIndex"), (int) jsonObject.get("pageSize"));

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        if (!StrUtil.isBlankIfStr(jsonObject.get("userName"))) {
            queryWrapper.like("user_name", jsonObject.get("userName"));
        }
        if (!StrUtil.isBlankIfStr(jsonObject.get("status"))) {
            queryWrapper.like("status", jsonObject.get("status"));
        }
        queryWrapper.select(UserInfo.class, info -> !"password".equals(info.getColumn()));
        //1-生效，0-失效
        queryWrapper.orderByAsc("id");
        Page<UserInfo> websiteListPage = this.userInfoService.page(queryPage, queryWrapper);
        return new ResultDto<>(websiteListPage.getTotal(), websiteListPage.getRecords());
    }

    /**
     * 描述：新增用户
     *
     * @return com.bztc.dto.ResultDto<com.bztc.entity.userInfo>
     * @author daism
     * @date 2022-09-29 10:35:10
     */
    @PostMapping("/insert")
    public ResultDto<UserInfo> insert(@RequestBody UserInfo userInfo) {
        logger.info("新增入参：{}", JSONUtil.toJsonStr(userInfo));
        ResultDto<UserInfo> resultDto = new ResultDto<>();
        userInfo.setStatus(Constants.STATUS_EFFECT);
        userInfo.setInputUser(Constants.ADMIN);
        userInfo.setUpdateUser(Constants.ADMIN);
        userInfo.setPassword(SecureUtil.md5("bztc.website"));
        try {
            this.userInfoService.save(userInfo);
            resultDto.setCode(200);
            resultDto.setData(userInfo);
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
     * @return com.bztc.dto.ResultDto<com.bztc.entity.userInfo>
     * @author daism
     * @date 2022-10-14 09:55:17
     */
    @PostMapping("/update")
    public ResultDto<UserInfo> update(@RequestBody UserInfo userInfo) {
        logger.info("更新入参：{}", JSONUtil.toJsonStr(userInfo));
        ResultDto<UserInfo> resultDto = new ResultDto<>();
        userInfo.setUpdateUser(Constants.ADMIN);
        try {
            this.userInfoService.updateById(userInfo);
            resultDto.setCode(200);
            resultDto.setData(userInfo);
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
    @Transactional(rollbackFor = Exception.class)
    public ResultDto<Integer> delete(@RequestBody UserInfo userInfo) {
        logger.info("删除入参：{}", JSONUtil.toJsonStr(userInfo));
        ResultDto<Integer> resultDto = new ResultDto<>();
        if (Constants.STATUS_EFFECT.equals(userInfo.getStatus())) {
            userInfo.setUpdateUser(Constants.ADMIN);
            userInfo.setStatus(Constants.STATUS_NOAVAIL);
            UpdateWrapper<UserInfo> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", userInfo.getId());
            wrapper.set("status", Constants.STATUS_NOAVAIL);
            this.userInfoService.update(wrapper);
        } else {
            this.userInfoService.removeById(userInfo);
            userRoleService.deleteByUserId(userInfo.getId());
        }
        resultDto.setCode(200);
        return resultDto;
    }

    /**
     * 查询某个角色未关联用户列表
     *
     * @return 用户
     */
    @GetMapping("/queryusersnoroles")
    public ResultDto<List<UserInfo>> queryUsersNoRoles(@RequestParam("param") String params) {
        logger.info("分页查询入参：{}", params);
        JSONObject jsonObject = JSONUtil.parseObj(params);

        Page<UserInfo> queryPage = new Page<>((int) jsonObject.get("pageIndex"), (int) jsonObject.get("pageSize"));
        Object roleId = jsonObject.get("roleId");
        Assert.notNull(roleId, "roleId不能为空");
        Page<UserInfo> listPage = this.userInfoService.queryUserNoRoles(queryPage, roleId.toString());
        return new ResultDto<>(listPage.getTotal(), listPage.getRecords());
    }

    /**
     * 根据用户id查询用户信息
     *
     * @return 用户
     */
    @GetMapping("/selectbyid")
    public ResultDto<UserInfoDto> selectById() {
        UserInfo userInfo = this.userInfoService.getById(UserUtil.getUserId());
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(userInfo.getId());
        userInfoDto.setUserName(userInfo.getUserName());
        userInfoDto.setPhone(userInfo.getPhone());
        userInfoDto.setEmail(userInfo.getEmail());
        userInfoDto.setAvatarUrl(userInfo.getAvatarUrl());
        return new ResultDto<>(userInfoDto);
    }
}
