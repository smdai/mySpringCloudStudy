package com.bztc.web.rest;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bztc.constant.Constants;
import com.bztc.domain.RoleInfo;
import com.bztc.domain.UserInfo;
import com.bztc.domain.UserRole;
import com.bztc.dto.ResultDto;
import com.bztc.service.UserRoleService;
import com.bztc.utils.UserUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author daism
 * @create 2022-10-17 15:35
 * @description 用户角色管理controller层
 */
@RestController
@RequestMapping("/api/userroleresource")
public class UserRoleResource {
    private static final Logger logger = LoggerFactory.getLogger(UserRoleResource.class);
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 查询用户角色列表
     *
     * @return 用户
     */
    @GetMapping("/querybypage")
    public ResultDto<List<Map<String, Object>>> querybypage(@RequestParam("param") String params) {
        logger.info("分页查询入参：{}", params);
        JSONObject jsonObject = JSONUtil.parseObj(params);
        Object userId = jsonObject.get("userId");
        Assert.notNull(userId, "userId不能为空");
        Page<UserRole> queryPage = new Page<>((int) jsonObject.get("pageIndex"), (int) jsonObject.get("pageSize"));
        Page<Map<String, Object>> userRolePage = this.userRoleService.selectUserRoleByUserId(queryPage, userId.toString());
        return new ResultDto<>(userRolePage.getTotal(), userRolePage.getRecords());
    }

    /**
     * 查询用户角色列表
     *
     * @return 用户
     */
    @GetMapping("/queryuserlistbyroleid")
    public ResultDto<List<Map<String, Object>>> queryUserListByRoleId(@RequestParam("param") String params) {
        logger.info("分页查询入参：{}", params);
        JSONObject jsonObject = JSONUtil.parseObj(params);
        Object roleId = jsonObject.get("roleId");
        Assert.notNull(roleId, "roleId不能为空");
        Page<UserRole> queryPage = new Page<>((int) jsonObject.get("pageIndex"), (int) jsonObject.get("pageSize"));
        Page<Map<String, Object>> userRolePage = this.userRoleService.selectUserRoleByRoleId(queryPage, roleId.toString());
        return new ResultDto<>(userRolePage.getTotal(), userRolePage.getRecords());
    }

    /**
     * 描述：新增用户角色
     *
     * @return com.bztc.dto.ResultDto<com.bztc.entity.roleInfo>
     * @author daism
     * @date 2022-09-29 10:35:10
     */
    @PostMapping("/insertuserrole")
    public ResultDto<Integer> insert(@RequestBody List<RoleInfo> roleInfos, int userId) {
        logger.info("新增入参：{}", JSONUtil.toJsonStr(roleInfos));
        Assert.notNull(userId, "userId不能为空");
        Assert.notEmpty(roleInfos, "未选择关联角色");
        List<UserRole> userRoleList = roleInfos.stream().map(it -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(it.getRoleId());
            userRole.setStatus(Constants.STATUS_EFFECT);
            userRole.setInputUser(UserUtil.getUserId());
            userRole.setUpdateUser(UserUtil.getUserId());
            return userRole;
        }).collect(Collectors.toList());
        return getIntegerResultDto(userRoleList);
    }

    /**
     * 描述：新增用户角色
     *
     * @return com.bztc.dto.ResultDto<com.bztc.entity.roleInfo>
     * @author daism
     * @date 2022-09-29 10:35:10
     */
    @PostMapping("/insertroleuser")
    public ResultDto<Integer> insertroleuser(@RequestBody List<UserInfo> userInfos, int roleId) {
        logger.info("新增入参：{}", JSONUtil.toJsonStr(userInfos));
        Assert.notNull(roleId, "roleId不能为空");
        Assert.notEmpty(userInfos, "未选择关联用户");
        List<UserRole> userRoleList = userInfos.stream().map(it -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(it.getId());
            userRole.setRoleId(roleId);
            userRole.setStatus(Constants.STATUS_EFFECT);
            userRole.setInputUser(UserUtil.getUserId());
            userRole.setUpdateUser(UserUtil.getUserId());
            return userRole;
        }).collect(Collectors.toList());
        return getIntegerResultDto(userRoleList);
    }

    @NotNull
    private ResultDto<Integer> getIntegerResultDto(List<UserRole> userRoleList) {
        ResultDto<Integer> resultDto = new ResultDto<>();
        try {
            this.userRoleService.saveBatch(userRoleList);
            resultDto.setCode(200);
        } catch (Exception e) {
            logger.error("插入数据库失败！", e);
            resultDto.setCode(400);
            resultDto.setMessage("插入数据库失败," + e.getMessage());
        }
        return resultDto;
    }

    /**
     * 描述：删除用户角色
     *
     * @return com.bztc.dto.ResultDto<com.bztc.entity.roleInfo>
     * @author daism
     * @date 2022-09-29 10:35:10
     */
    @PostMapping("/deleteuserrole")
    public ResultDto<Integer> delete(@RequestBody List<RoleInfo> roleInfos, int userId) {
        logger.info("新增入参：{}", JSONUtil.toJsonStr(roleInfos));
        Assert.notNull(userId, "userId不能为空");
        Assert.notEmpty(roleInfos, "未选择关联角色");
        ResultDto<Integer> resultDto = new ResultDto<>();
        List<Integer> roleIds = roleInfos.stream().map(RoleInfo::getRoleId).collect(Collectors.toList());
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id", roleIds).eq("user_id", userId);
        try {
            this.userRoleService.remove(queryWrapper);
            resultDto.setCode(200);
        } catch (Exception e) {
            logger.error("删除失败！", e);
            resultDto.setCode(400);
            resultDto.setMessage("删除失败," + e.getMessage());
        }
        return resultDto;
    }

    /**
     * 描述：删除用户角色
     *
     * @return com.bztc.dto.ResultDto<com.bztc.entity.roleInfo>
     * @author daism
     * @date 2022-09-29 10:35:10
     */
    @PostMapping("/deleteroleuser")
    public ResultDto<Integer> deleteroleuser(@RequestBody List<Map<String, Object>> userInfos, int roleId) {
        logger.info("新增入参：{}", JSONUtil.toJsonStr(userInfos));
        Assert.notNull(roleId, "roleId不能为空");
        Assert.notEmpty(userInfos, "未选择关联用户");
        ResultDto<Integer> resultDto = new ResultDto<>();
        List<Integer> userIds = userInfos.stream().map(it -> Integer.valueOf(String.valueOf(it.get("userId")))).collect(Collectors.toList());
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("user_id", userIds).eq("role_id", roleId);
        try {
            this.userRoleService.remove(queryWrapper);
            resultDto.setCode(200);
        } catch (Exception e) {
            logger.error("删除失败！", e);
            resultDto.setCode(400);
            resultDto.setMessage("删除失败," + e.getMessage());
        }
        return resultDto;
    }

    /**
     * 查询该用户是否拥有该角色
     *
     * @param roleId
     * @return
     */
    @GetMapping("/isrelrole")
    public ResultDto<Boolean> isRelRole(@RequestParam("roleId") int roleId) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", UserUtil.getUserId());
        queryWrapper.eq("role_id", roleId);
        queryWrapper.eq("status", Constants.STATUS_EFFECT);
        return new ResultDto<>(CollectionUtil.isNotEmpty(this.userRoleService.list(queryWrapper)));
    }
}
