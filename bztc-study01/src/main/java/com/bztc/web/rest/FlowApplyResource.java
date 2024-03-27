package com.bztc.web.rest;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.bztc.domain.FlowApply;
import com.bztc.domain.FlowModel;
import com.bztc.dto.FlowApplyQueryDto;
import com.bztc.dto.FlowSubmitDto;
import com.bztc.dto.ResultDto;
import com.bztc.enumeration.FlowPhaseEnum;
import com.bztc.service.FlowApplyService;
import com.bztc.service.FlowModelService;
import com.bztc.service.FlowTaskService;
import com.bztc.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author daism
 * @create 2024-03-25 17:08
 * @description 流程申请查询
 */
@Slf4j
@RestController
@RequestMapping("/api/flowapplyresource")
public class FlowApplyResource {
    @Autowired
    private FlowApplyService flowApplyService;
    @Autowired
    private FlowTaskService flowTaskService;

    @Autowired
    private FlowModelService flowModelService;

    /**
     * 查询申请信息
     *
     * @param objectType
     * @param objectNo
     * @return
     */
    @GetMapping("/queryapplybyobject")
    public ResultDto<FlowApplyQueryDto> queryApplyByObject(@RequestParam("objectType") String objectType, @RequestParam("objectNo") int objectNo) {
        log.info("查询申请信息入参：{},{}", objectType, objectNo);
        FlowApply flowApply = this.flowApplyService.queryFlowApplyByObject(objectType, objectNo);
        if (Objects.isNull(flowApply)) {
            return new ResultDto<>(400, "流程不存在。");
        }
        if (!flowApply.getInputUser().equals(UserUtil.getUserId())) {
            return new ResultDto<>(400, "您对该流程没有权限。");
        }
        if (!FlowPhaseEnum.APPLY.key.equals(flowApply.getPhaseNo())) {
            return new ResultDto<>(400, "非申请阶段，无法提交。");
        }
        //查询提交下一节点角色，下一阶段
        FlowApplyQueryDto flowApplyQueryDto = new FlowApplyQueryDto();
        FlowModel flowModel = flowModelService.getNextFlowModelByFlowNoNodeNo(flowApply.getFlowNo(), flowApply.getNodeNo());
        BeanUtil.copyProperties(flowApply, flowApplyQueryDto, false);
        flowApplyQueryDto.setNextPhaseNo(flowModel.getPhaseNo());
        flowApplyQueryDto.setNextApproveRole(flowModel.getNodeApproveRole());
        return new ResultDto<>(flowApplyQueryDto);
    }

    /**
     * 流程提交
     *
     * @param flowSubmitDto
     * @return
     */
    @PostMapping("/submit")
    public ResultDto<String> submit(@RequestBody FlowSubmitDto flowSubmitDto) {
        log.info("流程提交开始：{}", JSONUtil.toJsonStr(flowSubmitDto));
        FlowApply flowApply = this.flowApplyService.queryFlowApplyByObject(flowSubmitDto.getObjectType(), flowSubmitDto.getObjectNo());
        if (Objects.isNull(flowApply)) {
            return new ResultDto<>(400, "流程不存在。");
        }
        if (!flowApply.getInputUser().equals(UserUtil.getUserId())) {
            return new ResultDto<>(400, "您对该流程没有权限");
        }
        FlowModel flowModel = flowModelService.getNextFlowModelByFlowNoNodeNo(flowApply.getFlowNo(), flowApply.getNodeNo());
        return new ResultDto<>(this.flowApplyService.submit(flowSubmitDto, flowApply, flowModel));
    }
}
