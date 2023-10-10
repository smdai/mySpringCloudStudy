package com.bztc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bztc.domain.UserRole;

import java.util.List;
import java.util.Map;

/**
 * @author daishuming
 * @description 针对表【user_role(用户角色关联表)】的数据库操作Service
 * @createDate 2022-10-18 09:25:40
 */
public interface UserRoleService extends IService<UserRole> {
    /**
     * 查询用户角色
     *
     * @param queryPage
     * @return
     */
    Page<Map<String, Object>> selectUserRoleByUserId(Page<UserRole> queryPage, String userId);

    /**
     * 查询用户角色
     *
     * @param queryPage
     * @return
     */
    Page<Map<String, Object>> selectUserRoleByRoleId(Page<UserRole> queryPage, String roleId);

    /**
     * 根据roleid查询
     *
     * @param roleId
     * @return
     */
    List<UserRole> selectByRoleId(int roleId);
}
