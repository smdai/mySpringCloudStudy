package com.bztc.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.constant.RedisConstants;
import com.bztc.domain.RoleInfo;
import com.bztc.mapper.RoleInfoMapper;
import com.bztc.service.RoleInfoService;
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
 * @description 针对表【role_info(角色信息表)】的数据库操作Service实现
 * @createDate 2022-10-18 09:25:40
 */
@Service
public class RoleInfoServiceImpl extends ServiceImpl<RoleInfoMapper, RoleInfo>
        implements RoleInfoService {

    @Autowired
    RedisUtil redisUtil;

    /**
     * 查询用户未关联的角色
     *
     * @param rowPage
     * @param userId
     * @return
     */
    @Override
    public Page<RoleInfo> queryUserNoRoles(Page<RoleInfo> rowPage, String userId) {
        return this.baseMapper.queryUserNoRoles(rowPage, userId);
    }

    /**
     * 查询用户所拥有的角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<RoleInfo> queryUserRoleList(String userId) {
        return this.baseMapper.queryUserRoleList(userId);
    }

    /**
     * 根据userid获取userName
     *
     * @param roleId
     * @return
     */
    @Override
    public String getRoleNameByRoleId(int roleId) {
        if (roleId <= 0) {
            return StrUtil.EMPTY;
        }
        Object o = redisUtil.get(RedisConstants.SYSTEM_ROLE_NAME_REL_ID);
        if (Objects.isNull(o) || StrUtil.isEmpty(((Map<Integer, String>) o).get(roleId))) {
            //查询数据库
            RoleInfo roleInfo = this.baseMapper.selectById(roleId);
            //刷新缓存
            refreshRoleInfo();
            return Objects.isNull(roleInfo) ? StringUtils.EMPTY : roleInfo.getRoleName();
        } else {
            return ((Map<Integer, String>) o).get(roleId);
        }
    }

    /**
     * 刷新缓存到redis
     */
    private void refreshRoleInfo() {
        List<RoleInfo> roleInfos = this.baseMapper.selectList(new QueryWrapper<>());
        Map<Integer, String> map = new HashMap<>();
        roleInfos.forEach(it -> {
            map.put(it.getRoleId(), it.getRoleName());
        });
        redisUtil.set(RedisConstants.SYSTEM_ROLE_NAME_REL_ID, map);
    }
}




