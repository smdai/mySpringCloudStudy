package com.bztc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bztc.domain.UserInfo;
import com.bztc.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author daishuming
 * @description 针对表【user_info(用户信息表)】的数据库操作Mapper
 * @createDate 2022-10-14 16:45:30
 * @Entity com.bztc.domain.UserInfo
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    /**
     * 查询未关联角色的用户
     *
     * @param rowPage
     * @param roleId
     * @return
     */
    Page<UserInfo> queryUserNoRoles(Page<UserInfo> rowPage, @Param("roleId") String roleId);

    /**
     * 分页查询用户信息
     *
     * @param rowPage
     * @param userRole
     * @return
     */
    Page<Map<String, Object>> selectUserByPage(Page<UserRole> rowPage, @Param("userRole") Map<String, Object> userRole);
}




