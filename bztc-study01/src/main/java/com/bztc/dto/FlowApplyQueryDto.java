package com.bztc.dto;

import com.bztc.domain.FlowApply;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author daism
 * @create 2024-03-25 17:11
 * @description 申请查询
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FlowApplyQueryDto extends FlowApply {
    private static final long serialVersionUID = -6684795771637511811L;
    /**
     * 下一阶段号
     */
    private String nextPhaseNo;
    /**
     * 下一审批角色
     */
    private String nextApproveRole;
}
