package com.bztc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.domain.UserRole;
import com.bztc.service.UserRoleService;
import com.bztc.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author daishuming
* @description 针对表【user_role(用户角色关联表)】的数据库操作Service实现
* @createDate 2022-10-18 09:25:39
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




