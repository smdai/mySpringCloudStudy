package com.bztc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bztc.domain.AuthResContr;
import com.bztc.dto.ResultDto;
import com.bztc.dto.SessionInfoDto;

/**
 * @author daishuming
 * @description 针对表【auth_res_contr(权限关联表)】的数据库操作Service
 * @createDate 2022-10-18 09:25:40
 */
public interface AuthResContrService extends IService<AuthResContr> {
    /**
     * 描述：获取session
     *
     * @return com.bztc.dto.ResultDto<com.bztc.dto.SessionInfoDto>
     * @author daism
     * @date 2022-10-18 18:45:55
     */
    ResultDto<SessionInfoDto> getSession();
}
