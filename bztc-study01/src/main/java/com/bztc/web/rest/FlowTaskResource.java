package com.bztc.web.rest;

import cn.hutool.core.collection.CollectionUtil;
import com.bztc.domain.FlowApply;
import com.bztc.domain.FlowOpinion;
import com.bztc.dto.FlowTaskDto;
import com.bztc.dto.ResultDto;
import com.bztc.service.FlowApplyService;
import com.bztc.service.FlowOpinionService;
import com.bztc.service.FlowTaskService;
import com.bztc.service.UserInfoService;
import com.bztc.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @author daism
 * @create 2024-03-27 15:56
 * @description 流程记录
 */
@Slf4j
@RestController
@RequestMapping("/api/flowtaskresource")
public class FlowTaskResource {
    @Autowired
    private FlowTaskService flowTaskService;
    @Autowired
    private FlowApplyService flowApplyService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private FlowOpinionService flowOpinionService;

    /**
     * 查询流程记录
     *
     * @param objectType
     * @param objectNo
     * @return
     */
    @GetMapping("/queryflowtasklist")
    public ResultDto<List<FlowTaskDto>> queryFlowTaskList(@RequestParam("objectType") String objectType, @RequestParam("objectNo") int objectNo) {
        FlowApply flowApply = flowApplyService.queryFlowApplyByObject(objectType, objectNo);
        if (Objects.isNull(flowApply)) {
            return new ResultDto<>(400, "流程不存在。");
        }
        if (!flowApply.getInputUser().equals(UserUtil.getUserId())) {
            return new ResultDto<>(400, "您对该流程没有权限");
        }
        List<FlowTaskDto> flowTaskDtos = this.flowTaskService.queryFlowTaskList(objectType, objectNo, flowApply.getFlowNo());
        if (CollectionUtil.isNotEmpty(flowTaskDtos)) {
            flowTaskDtos.forEach(it -> {
                it.setApproveUserName(this.userInfoService.getUserNameByUserId(it.getNodeApproveUser()));
                //查询流程意见
                FlowOpinion flowOpinion = flowOpinionService.queryByObjectAndNode(objectType, objectNo, it.getNodeNo());
                it.setOpinion(Objects.isNull(flowOpinion) ? "" : flowOpinion.getOpinion());
            });
        }
        return new ResultDto<>(flowTaskDtos);
    }
}
