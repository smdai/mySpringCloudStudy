package com.bztc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bztc.domain.FlowOpinion;

/**
 * @author daishuming
 * @description 针对表【flow_opinion(流程意见表)】的数据库操作Service
 * @createDate 2024-03-21 11:32:49
 */
public interface FlowOpinionService extends IService<FlowOpinion> {
    /**
     * 根据对象类型，编号查询意见
     *
     * @param objectType
     * @param objectNo
     * @return
     */
    FlowOpinion queryByObjectAndNode(String objectType, int objectNo, String nodeNo);
}
