package com.bztc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bztc.domain.FlowTask;
import com.bztc.dto.FlowTaskDto;

import java.util.List;

/**
 * @author daishuming
 * @description 针对表【flow_task(流程任务表)】的数据库操作Service
 * @createDate 2024-03-21 11:32:49
 */
public interface FlowTaskService extends IService<FlowTask> {
    /**
     * 查询流程记录
     *
     * @param objectType
     * @param objectNo
     * @return
     */
    List<FlowTaskDto> queryFlowTaskList(String objectType, int objectNo, String flowNo);
}
