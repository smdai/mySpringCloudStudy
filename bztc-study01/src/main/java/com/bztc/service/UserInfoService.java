package com.bztc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bztc.domain.UserInfo;
import com.bztc.dto.SessionInfoDto;
import com.bztc.enumeration.LoginEnum;
import org.apache.ibatis.annotations.Param;

/**
 * @author daishuming
 * @description 针对表【user_info(用户信息表)】的数据库操作Service
 * @createDate 2022-10-14 16:45:30
 */
public interface UserInfoService extends IService<UserInfo> {
    /**
     * 描述：用户登录
     *
     * @param userInfo 用户信息
     * @return {@link LoginEnum}
     * @author daism
     * @date 2022-10-14 17:22:28
     */
    SessionInfoDto login(UserInfo userInfo);

    /**
     * 查询未关联角色的用户
     *
     * @param rowPage
     * @param roleId
     * @return
     */
    Page<UserInfo> queryUserNoRoles(Page<UserInfo> rowPage, @Param("roleId") String roleId);
}
