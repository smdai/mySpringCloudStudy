package com.bztc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bztc.domain.AuthResContr;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author daishuming
 * @description 针对表【auth_res_contr(权限关联表)】的数据库操作Mapper
 * @createDate 2022-10-18 09:25:40
 * @Entity com.bztc.domain.AuthResContr
 */
@Mapper
public interface AuthResContrMapper extends BaseMapper<AuthResContr> {

}




