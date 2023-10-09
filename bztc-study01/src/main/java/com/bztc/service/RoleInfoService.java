package com.bztc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bztc.domain.RoleInfo;

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
}
