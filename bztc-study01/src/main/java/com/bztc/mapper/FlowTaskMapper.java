package com.bztc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bztc.domain.FlowTask;
import com.bztc.dto.FlowTaskDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author daishuming
 * @description 针对表【flow_task(流程任务表)】的数据库操作Mapper
 * @createDate 2024-03-21 11:32:49
 * @Entity com.bztc.domain.FlowTask
 */
@Mapper
public interface FlowTaskMapper extends BaseMapper<FlowTask> {
    /**
     * 查询流程记录
     *
     * @param map
     * @return
     */
    List<FlowTaskDto> queryFlowTaskList(@Param("map") Map<String, Object> map);
}




