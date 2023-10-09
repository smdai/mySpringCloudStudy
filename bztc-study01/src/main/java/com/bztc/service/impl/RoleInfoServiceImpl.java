package com.bztc.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.domain.RoleInfo;
import com.bztc.mapper.RoleInfoMapper;
import com.bztc.service.RoleInfoService;
import org.springframework.stereotype.Service;

/**
 * @author daishuming
 * @description 针对表【role_info(角色信息表)】的数据库操作Service实现
 * @createDate 2022-10-18 09:25:40
 */
@Service
public class RoleInfoServiceImpl extends ServiceImpl<RoleInfoMapper, RoleInfo>
        implements RoleInfoService {

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
}




