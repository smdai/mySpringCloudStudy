package com.bztc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bztc.domain.RoleInfo;

import java.util.List;

/**
 * @author daishuming
 * @description 针对表【role_info(角色信息表)】的数据库操作Service
 * @createDate 2022-10-18 09:25:40
 */
public interface RoleInfoService extends IService<RoleInfo> {
    /**
     * 查询用户未关联的角色
     *
     * @param rowPage
     * @param userId
     * @return
     */
    Page<RoleInfo> queryUserNoRoles(Page<RoleInfo> rowPage, String userId);

    /**
     * 查询用户所拥有的角色
     *
     * @param userId
     * @return
     */
    List<RoleInfo> queryUserRoleList(String userId);

    /**
     * 根据roleid获取userName
     *
     * @param roleId
     * @return
     */
    String getRoleNameByRoleId(int roleId);
}
