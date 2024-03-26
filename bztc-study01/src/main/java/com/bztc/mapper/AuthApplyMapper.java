package com.bztc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bztc.domain.AuthApply;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author daishuming
 * @description 针对表【auth_apply(权限申请表)】的数据库操作Mapper
 * @createDate 2024-03-21 11:32:49
 * @Entity com.bztc.domain.AuthApply
 */
public interface AuthApplyMapper extends BaseMapper<AuthApply> {
    Page<Map<String, Object>> selectByPage(IPage<Map<String, Object>> page, @Param("paramMap") Map<String, Object> paramMap);

}




