package com.bztc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.constant.Constants;
import com.bztc.domain.ControlInfo;
import com.bztc.dto.AuthSourceDto;
import com.bztc.mapper.ControlInfoMapper;
import com.bztc.service.ControlInfoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author daishuming
 * @description 针对表【control_info(控制点信息表)】的数据库操作Service实现
 * @createDate 2023-10-30 11:31:41
 */
@Service
public class ControlInfoServiceImpl extends ServiceImpl<ControlInfoMapper, ControlInfo>
        implements ControlInfoService {

    /**
     * 根据菜单id查询控制点
     *
     * @param menuId
     * @return
     */
    @Override
    public List<AuthSourceDto> queryControlByMenuId(int menuId) {
        QueryWrapper<ControlInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menu_id", menuId).eq("status", Constants.STATUS_EFFECT);
        List<ControlInfo> controlInfos = this.baseMapper.selectList(queryWrapper);
        return controlInfos.stream().map(it -> {
            AuthSourceDto authSourceDto = new AuthSourceDto();
            authSourceDto.setObjectId(Constants.RES_CONTR_TYPE_C + it.getControlId());
            authSourceDto.setSourceId(it.getControlId());
            authSourceDto.setSourceName(it.getControlName());
            authSourceDto.setSourceType(Constants.RES_CONTR_TYPE_C);
            authSourceDto.setLabel(it.getControlName());
            return authSourceDto;
        }).collect(Collectors.toList());
    }
}




