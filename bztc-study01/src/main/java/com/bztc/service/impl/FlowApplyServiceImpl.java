package com.bztc.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.constant.Constants;
import com.bztc.domain.*;
import com.bztc.dto.FlowSubmitDto;
import com.bztc.enumeration.FlowPhaseEnum;
import com.bztc.mapper.FlowApplyMapper;
import com.bztc.mapper.FlowCatalogMapper;
import com.bztc.mapper.FlowOpinionMapper;
import com.bztc.mapper.FlowTaskMapper;
import com.bztc.service.FlowApplyService;
import com.bztc.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author daishuming
 * @description 针对表【flow_apply(流程申请表)】的数据库操作Service实现
 * @createDate 2024-03-21 11:32:49
 */
@Service
@Slf4j
public class FlowApplyServiceImpl extends ServiceImpl<FlowApplyMapper, FlowApply>
        implements FlowApplyService {
    @Autowired
    private FlowCatalogMapper flowCatalogMapper;
    @Autowired
    private FlowTaskMapper flowTaskMapper;
    @Autowired
    private FlowOpinionMapper flowOpinionMapper;

    /**
     * 初始化流程
     *
     * @param flowNo     流程号
     * @param applyType  申请类型
     * @param objectType 对象类型
     * @param objectNo   对象编号
     */
    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void initFlow(String flowNo, String applyType, String objectType, int objectNo) {
        log.info("初始化流程开始：{},{},{},{}", flowNo, applyType, objectType, objectNo);
        Assert.notBlank(flowNo, "流程号不能为空。");
        Assert.notBlank(applyType, "申请类型不能为空。");
        Assert.notBlank(objectType, "对象类型不能为空。");
        Assert.notNull(objectNo, "对象编号不能为空。");
        //查询流程配置
        QueryWrapper<FlowCatalog> flowCatalogQueryWrapper = new QueryWrapper<>();
        flowCatalogQueryWrapper.eq("flow_no", flowNo);
        FlowCatalog flowCatalog = flowCatalogMapper.selectOne(flowCatalogQueryWrapper);
        Assert.notNull(flowCatalog, "流程未配置。");
        //插入流程申请表
        FlowApply flowApply = new FlowApply();
        flowApply.setApplyType(applyType);
        flowApply.setObjectType(objectType);
        flowApply.setObjectNo(objectNo);
        flowApply.setFlowNo(flowNo);
        flowApply.setPhaseNo(FlowPhaseEnum.APPLY.key);
        flowApply.setPhaseName(FlowPhaseEnum.APPLY.value);
        flowApply.setNodeNo(Constants.INIT_FLOW_NODE);
        flowApply.setInputUser(UserUtil.getUserId());
        flowApply.setUpdateUser(UserUtil.getUserId());
        this.baseMapper.insert(flowApply);
        //插入流程任务表
        FlowTask flowTask = new FlowTask();
        flowTask.setObjectType(objectType);
        flowTask.setObjectNo(objectNo);
        flowTask.setNodeNo(Constants.INIT_FLOW_NODE);
        flowTask.setBeginTime(new Date());
        flowTaskMapper.insert(flowTask);
    }

    /**
     * 判断是否有在途流程，true-有在途，false-无在途
     *
     * @param objectType
     * @param inputUser
     * @return
     */
    @Override
    public boolean isApplyingFlow(String objectType, int inputUser) {
        Assert.notBlank(objectType, "对象类型不能为空。");
        Assert.notNull(inputUser, "用户id不能为空。");
        QueryWrapper<FlowApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("object_type", objectType);
        queryWrapper.eq("input_user", inputUser);
        queryWrapper.notIn("phase_no", FlowPhaseEnum.AGREE.key, FlowPhaseEnum.REJECT.key);
        List<FlowApply> flowApplies = this.baseMapper.selectList(queryWrapper);
        return CollectionUtil.isNotEmpty(flowApplies);
    }

    /**
     * 是否在申请阶段
     * todo 退回到申请人
     *
     * @param objectType
     * @param objectNo
     * @return
     */
    @Override
    public boolean isApplyPhase(String objectType, int objectNo) {
        Assert.notBlank(objectType, "对象类型不能为空。");
        Assert.notNull(objectNo, "对象编号不能为空。");
        QueryWrapper<FlowApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("object_type", objectType);
        queryWrapper.eq("object_no", objectNo);
        queryWrapper.eq("phase_no", FlowPhaseEnum.APPLY.key);
        List<FlowApply> flowApplies = this.baseMapper.selectList(queryWrapper);
        return CollectionUtil.isNotEmpty(flowApplies);
    }

    /**
     * 删除流程
     *
     * @param objectType
     * @param objectNo
     */
    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void deleteFlow(String objectType, int objectNo) {
        QueryWrapper<FlowApply> flowApplyQueryWrapper = new QueryWrapper<>();
        flowApplyQueryWrapper.eq("object_type", objectType);
        flowApplyQueryWrapper.eq("object_no", objectNo);
        this.baseMapper.delete(flowApplyQueryWrapper);

        QueryWrapper<FlowTask> flowTaskQueryWrapper = new QueryWrapper<>();
        flowTaskQueryWrapper.eq("object_type", objectType);
        flowTaskQueryWrapper.eq("object_no", objectNo);
        this.flowTaskMapper.delete(flowTaskQueryWrapper);

        QueryWrapper<FlowOpinion> flowOpinionQueryWrapper = new QueryWrapper<>();
        flowOpinionQueryWrapper.eq("object_type", objectType);
        flowOpinionQueryWrapper.eq("object_no", objectNo);
        this.flowOpinionMapper.delete(flowOpinionQueryWrapper);
    }

    /**
     * 检查流程是否存在 true-存在，false-不存在
     *
     * @param objectType
     * @param objectNo
     * @return
     */
    @Override
    public FlowApply queryFlowApplyByObject(String objectType, int objectNo) {
        QueryWrapper<FlowApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("object_type", objectType).eq("object_no", objectNo);
        return this.baseMapper.selectOne(queryWrapper);
    }

    /**
     * 流程提交
     *
     * @param flowSubmitDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public String submit(FlowSubmitDto flowSubmitDto, FlowModel nextFlowModel) {
        //更新flow_apply
        QueryWrapper<FlowApply> flowApplyQueryWrapper = new QueryWrapper<>();
        flowApplyQueryWrapper.eq("object_type", flowSubmitDto.getObjectType()).eq("object_no", flowSubmitDto.getObjectNo());
        FlowApply flowApply = new FlowApply();
        flowApply.setNodeNo(nextFlowModel.getNodeNo());
        flowApply.setPhaseName(nextFlowModel.getPhaseName());
        flowApply.setPhaseNo(nextFlowModel.getPhaseNo());
        this.baseMapper.update(flowApply, flowApplyQueryWrapper);
        //插入flow_task
        FlowTask flowTask = new FlowTask();
        flowTask.setObjectType(flowSubmitDto.getObjectType());
        flowTask.setObjectNo(flowSubmitDto.getObjectNo());
        flowTask.setNodeNo(nextFlowModel.getNodeNo());
        flowTask.setBeginTime(new Date());
        flowTask.setNodeApproveUser(flowSubmitDto.getNextApproveUser());
        this.flowTaskMapper.insert(flowTask);
        //插入flow_opinion
        FlowOpinion flowOpinion = new FlowOpinion();
        flowOpinion.setObjectType(flowSubmitDto.getObjectType());
        flowOpinion.setObjectNo(flowSubmitDto.getObjectNo());
        flowOpinion.setOpinion(flowSubmitDto.getOpinion());
        flowOpinion.setInputUser(UserUtil.getUserId());
        flowOpinion.setUpdateUser(UserUtil.getUserId());
        flowOpinion.setInputTime(new Date());
        this.flowOpinionMapper.insert(flowOpinion);
        return "success";
    }
}




