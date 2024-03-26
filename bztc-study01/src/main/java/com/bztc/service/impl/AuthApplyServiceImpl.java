package com.bztc.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.domain.AuthApply;
import com.bztc.mapper.AuthApplyMapper;
import com.bztc.service.AuthApplyService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author daishuming
 * @description 针对表【auth_apply(权限申请表)】的数据库操作Service实现
 * @createDate 2024-03-21 11:32:49
 */
@Service
public class AuthApplyServiceImpl extends ServiceImpl<AuthApplyMapper, AuthApply>
        implements AuthApplyService {

    /**
     * @param page
     * @param paramMap
     * @return
     */
    @Override
    public Page<Map<String, Object>> selectPage(Page<Map<String, Object>> page, Map<String, Object> paramMap) {
        return this.baseMapper.selectByPage(page, paramMap);
    }
}




