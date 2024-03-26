package com.bztc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bztc.domain.RoleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author daishuming
 * @description 针对表【role_info(角色信息表)】的数据库操作Mapper
 * @createDate 2022-10-18 09:25:40
 * @Entity com.bztc.domain.RoleInfo
 */
@Mapper
public interface RoleInfoMapper extends BaseMapper<RoleInfo> {
    /**
     * 查询用户未关联的角色
     *
     * @param rowPage
     * @param userId
     * @return
     */
    Page<RoleInfo> queryUserNoRoles(Page<RoleInfo> rowPage, @Param("userId") String userId);

    /**
     * 查询用户所拥有的角色
     *
     * @param userId
     * @return
     */
    List<RoleInfo> queryUserRoleList(@Param("userId") String userId);
}




