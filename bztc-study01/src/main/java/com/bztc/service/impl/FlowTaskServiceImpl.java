package com.bztc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.domain.FlowTask;
import com.bztc.dto.FlowTaskDto;
import com.bztc.mapper.FlowTaskMapper;
import com.bztc.service.FlowTaskService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author daishuming
 * @description 针对表【flow_task(流程任务表)】的数据库操作Service实现
 * @createDate 2024-03-21 11:32:49
 */
@Service
public class FlowTaskServiceImpl extends ServiceImpl<FlowTaskMapper, FlowTask>
        implements FlowTaskService {

    /**
     * 查询流程记录
     *
     * @param objectType
     * @param objectNo
     * @return
     */
    @Override
    public List<FlowTaskDto> queryFlowTaskList(String objectType, int objectNo, String flowNo) {
        Map<String, Object> map = new HashMap<>();
        map.put("objectType", objectType);
        map.put("objectNo", objectNo);
        map.put("flowNo", flowNo);
        return this.baseMapper.queryFlowTaskList(map);
    }
}




