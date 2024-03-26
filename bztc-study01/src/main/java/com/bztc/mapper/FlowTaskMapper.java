package com.bztc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bztc.domain.FlowTask;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author daishuming
 * @description 针对表【flow_task(流程任务表)】的数据库操作Mapper
 * @createDate 2024-03-21 11:32:49
 * @Entity com.bztc.domain.FlowTask
 */
@Mapper
public interface FlowTaskMapper extends BaseMapper<FlowTask> {

}




