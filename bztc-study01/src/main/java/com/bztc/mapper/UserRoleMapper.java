package com.bztc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bztc.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author daishuming
 * @description 针对表【user_role(用户角色关联表)】的数据库操作Mapper
 * @createDate 2022-10-18 09:25:40
 * @Entity com.bztc.domain.UserRole
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
    /**
     * 查询用户角色
     *
     * @param userId
     * @return
     */
    Page<Map<String, Object>> selectUserRoleByUserId(Page<UserRole> rowPage, @Param("userId") String userId);

    /**
     * 查询用户角色
     *
     * @param roleId
     * @return
     */
    Page<Map<String, Object>> selectUserRoleByRoleId(Page<UserRole> rowPage, @Param("roleId") String roleId);
}




