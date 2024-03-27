package com.bztc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bztc.domain.FlowApply;
import com.bztc.domain.FlowModel;
import com.bztc.dto.FlowSubmitDto;

/**
 * @author daishuming
 * @description 针对表【flow_apply(流程申请表)】的数据库操作Service
 * @createDate 2024-03-21 11:32:49
 */
public interface FlowApplyService extends IService<FlowApply> {
    /**
     * 初始化流程
     *
     * @param flowNo     流程号
     * @param applyType  申请类型
     * @param objectType 对象类型
     * @param objectNo   对象编号
     */
    void initFlow(String flowNo, String applyType, String objectType, int objectNo);

    /**
     * 判断是否有在途流程，true-有在途，false-无在途
     *
     * @param objectType
     * @param inputUser
     * @return
     */
    boolean isApplyingFlow(String objectType, int inputUser);

    /**
     * 是否在申请阶段
     *
     * @param objectType
     * @param objectNo
     * @return
     */
    boolean isApplyPhase(String objectType, int objectNo);

    /**
     * 删除流程
     *
     * @param objectType
     * @param objectNo
     */
    void deleteFlow(String objectType, int objectNo);

    /**
     * 查询申请信息
     *
     * @param objectType
     * @param objectNo
     * @return
     */
    FlowApply queryFlowApplyByObject(String objectType, int objectNo);

    /**
     * 流程提交
     *
     * @param flowSubmitDto
     * @return
     */
    String submit(FlowSubmitDto flowSubmitDto, FlowApply localFlowApply, FlowModel nextFlowModel);
}
