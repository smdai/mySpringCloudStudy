package com.bztc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.domain.FlowOpinion;
import com.bztc.mapper.FlowOpinionMapper;
import com.bztc.service.FlowOpinionService;
import org.springframework.stereotype.Service;

/**
 * @author daishuming
 * @description 针对表【flow_opinion(流程意见表)】的数据库操作Service实现
 * @createDate 2024-03-21 11:32:49
 */
@Service
public class FlowOpinionServiceImpl extends ServiceImpl<FlowOpinionMapper, FlowOpinion>
        implements FlowOpinionService {

    /**
     * 根据对象类型，编号查询意见
     *
     * @param objectType
     * @param objectNo
     * @return
     */
    @Override
    public FlowOpinion queryByObjectAndNode(String objectType, int objectNo, String nodeNo) {
        QueryWrapper<FlowOpinion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("object_type", objectType).eq("object_no", objectNo).eq("node_no", nodeNo);
        return this.baseMapper.selectOne(queryWrapper);
    }
}




