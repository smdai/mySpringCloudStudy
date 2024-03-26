package com.bztc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bztc.domain.FlowModel;

/**
 * @author daishuming
 * @description 针对表【flow_model(流程配置表)】的数据库操作Service
 * @createDate 2024-03-21 11:32:49
 */
public interface FlowModelService extends IService<FlowModel> {
    /**
     * 根据流程号和节点获取流程信息
     *
     * @param flowNo
     * @param nodeNo
     * @return
     */
    FlowModel getNextFlowModelByFlowNoNodeNo(String flowNo, String nodeNo);
}
