package com.bztc.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.constant.RedisConstants;
import com.bztc.domain.FlowModel;
import com.bztc.dto.FlowCatalogModelDto;
import com.bztc.mapper.FlowModelMapper;
import com.bztc.service.FlowModelService;
import com.bztc.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author daishuming
 * @description 针对表【flow_model(流程配置表)】的数据库操作Service实现
 * @createDate 2024-03-21 11:32:49
 */
@Service
public class FlowModelServiceImpl extends ServiceImpl<FlowModelMapper, FlowModel>
        implements FlowModelService {
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 根据流程号和节点获取下一阶段流程信息
     *
     * @param flowNo
     * @param nodeNo
     * @return
     */
    @Override
    public FlowModel getNextFlowModelByFlowNoNodeNo(String flowNo, String nodeNo) {
        Object o = redisUtil.get(RedisConstants.SYSTEM_FLOW + flowNo);
        Assert.notNull(o, "流程：{}，查不到，请刷新缓存。", flowNo);
        FlowCatalogModelDto flowCatalogModelDto = (FlowCatalogModelDto) o;
        FlowModel localFlowModel = flowCatalogModelDto.getFlowModelList().stream().filter(it -> nodeNo.equals(it.getNodeNo())).findFirst().get();
        return flowCatalogModelDto.getFlowModelList().stream().filter(it -> localFlowModel.getNextNodeNo().equals(it.getNodeNo())).findFirst().get();
    }
}




