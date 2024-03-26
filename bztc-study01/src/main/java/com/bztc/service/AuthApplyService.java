package com.bztc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bztc.domain.AuthApply;

import java.util.Map;

/**
 * @author daishuming
 * @description 针对表【auth_apply(权限申请表)】的数据库操作Service
 * @createDate 2024-03-21 11:32:49
 */
public interface AuthApplyService extends IService<AuthApply> {
    Page<Map<String, Object>> selectPage(Page<Map<String, Object>> page, Map<String, Object> paramMap);
}
