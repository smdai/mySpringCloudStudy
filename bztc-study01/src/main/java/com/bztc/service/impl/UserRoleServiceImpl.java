package com.bztc.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.domain.UserRole;
import com.bztc.mapper.UserRoleMapper;
import com.bztc.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author daishuming
 * @description 针对表【user_role(用户角色关联表)】的数据库操作Service实现
 * @createDate 2022-10-18 09:25:39
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
        implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 查询用户角色
     *
     * @param queryPage
     * @return
     */
    @Override
    public Page<Map<String, Object>> selectUserRoleByUserId(Page<UserRole> queryPage, String userId) {
        return userRoleMapper.selectUserRoleByUserId(queryPage, userId);
    }
}




