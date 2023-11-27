package com.bztc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bztc.domain.ControlInfo;
import com.bztc.dto.AuthSourceDto;

import java.util.List;

/**
 * @author daishuming
 * @description 针对表【control_info(控制点信息表)】的数据库操作Service
 * @createDate 2023-10-30 11:31:41
 */
public interface ControlInfoService extends IService<ControlInfo> {
    /**
     * 根据菜单id查询控制点
     *
     * @param menuId
     * @return
     */
    List<AuthSourceDto> queryControlByMenuId(int menuId);
}
