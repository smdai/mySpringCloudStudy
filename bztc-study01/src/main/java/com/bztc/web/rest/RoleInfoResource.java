package com.bztc.web.rest;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bztc.constant.Constants;
import com.bztc.domain.RoleInfo;
import com.bztc.domain.UserRole;
import com.bztc.dto.ResultDto;
import com.bztc.service.RoleInfoService;
import com.bztc.service.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author daism
 * @create 2022-10-17 15:35
 * @description 角色管理controller层
 */
@RestController
@RequestMapping("/api/roleinforesource")
public class RoleInfoResource {
    private static final Logger logger = LoggerFactory.getLogger(RoleInfoResource.class);
    @Autowired
    private RoleInfoService roleInfoService;
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 查询角色列表
     *
     * @return 用户
     */
    @GetMapping("/querybypage")
    public ResultDto<List<RoleInfo>> querybypage(@RequestParam("param") String params) {
        logger.info("分页查询入参：{}", params);
        JSONObject jsonObject = JSONUtil.parseObj(params);

        Page<RoleInfo> queryPage = new Page<>((int) jsonObject.get("pageIndex"), (int) jsonObject.get("pageSize"));

        QueryWrapper<RoleInfo> queryWrapper = new QueryWrapper<>();
        if (!StrUtil.isBlankIfStr(jsonObject.get("roleName"))) {
            queryWrapper.like("role_name", jsonObject.get("roleName"));
        }
        if (!StrUtil.isBlankIfStr(jsonObject.get("status"))) {
            queryWrapper.like("status", jsonObject.get("status"));
        }
        //1-生效，0-失效
        queryWrapper.orderByAsc("role_id");
        Page<RoleInfo> websiteListPage = this.roleInfoService.page(queryPage, queryWrapper);
        return new ResultDto<>(websiteListPage.getTotal(), websiteListPage.getRecords());
    }

    /**
     * 查询某个用户未关联的角色列表
     *
     * @return 用户
     */
    @GetMapping("/queryusernoroles")
    public ResultDto<List<RoleInfo>> queryUserNoRoles(@RequestParam("param") String params) {
        logger.info("分页查询入参：{}", params);
        JSONObject jsonObject = JSONUtil.parseObj(params);

        Page<RoleInfo> queryPage = new Page<>((int) jsonObject.get("pageIndex"), (int) jsonObject.get("pageSize"));
        Object userId = jsonObject.get("userId");
        Assert.notNull(userId, "userId不能为空");
        Page<RoleInfo> websiteListPage = this.roleInfoService.queryUserNoRoles(queryPage, userId.toString());
        return new ResultDto<>(websiteListPage.getTotal(), websiteListPage.getRecords());
    }

    /**
     * 描述：新增菜单
     *
     * @return com.bztc.dto.ResultDto<com.bztc.entity.roleInfo>
     * @author daism
     * @date 2022-09-29 10:35:10
     */
    @PostMapping("/insert")
    public ResultDto<RoleInfo> insert(@RequestBody RoleInfo roleInfo) {
        logger.info("新增入参：{}", JSONUtil.toJsonStr(roleInfo));
        ResultDto<RoleInfo> resultDto = new ResultDto<>();
        roleInfo.setStatus(Constants.STATUS_EFFECT);
        roleInfo.setInputUser(Constants.ADMIN);
        roleInfo.setUpdateUser(Constants.ADMIN);
        try {
            this.roleInfoService.save(roleInfo);
            resultDto.setCode(200);
            resultDto.setData(roleInfo);
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
     * @return com.bztc.dto.ResultDto<com.bztc.entity.roleInfo>
     * @author daism
     * @date 2022-10-14 09:55:17
     */
    @PostMapping("/update")
    public ResultDto<RoleInfo> update(@RequestBody RoleInfo roleInfo) {
        logger.info("更新入参：{}", JSONUtil.toJsonStr(roleInfo));
        ResultDto<RoleInfo> resultDto = new ResultDto<>();
        roleInfo.setUpdateUser(Constants.ADMIN);
        try {
            this.roleInfoService.updateById(roleInfo);
            resultDto.setCode(200);
            resultDto.setData(roleInfo);
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
    public ResultDto<Integer> delete(@RequestBody RoleInfo roleInfo) {
        logger.info("删除入参：{}", JSONUtil.toJsonStr(roleInfo));
        ResultDto<Integer> resultDto = new ResultDto<>();
        try {
            //校验-关联的用户不能删除
            List<UserRole> userRoleList = userRoleService.selectByRoleId(roleInfo.getRoleId());
            if (CollectionUtil.isNotEmpty(userRoleList)) {
                resultDto.setCode(400);
                resultDto.setMessage("有关联用户，不能删除。");
                return resultDto;
            }
            if (Constants.STATUS_EFFECT.equals(roleInfo.getStatus())) {
                roleInfo.setUpdateUser(Constants.ADMIN);
                roleInfo.setStatus(Constants.STATUS_NOAVAIL);
                UpdateWrapper<RoleInfo> wrapper = new UpdateWrapper<>();
                wrapper.eq("role_id", roleInfo.getRoleId());
                wrapper.set("status", Constants.STATUS_NOAVAIL);
                this.roleInfoService.update(wrapper);
            } else {
                this.roleInfoService.removeById(roleInfo);
            }
            resultDto.setCode(200);
        } catch (Exception e) {
            logger.error("删除数据库失败！", e);
            resultDto.setCode(400);
            resultDto.setMessage("删除数据库失败," + e.getMessage());
        }
        return resultDto;
    }
}
