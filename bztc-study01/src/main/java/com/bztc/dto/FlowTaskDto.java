package com.bztc.dto;

import com.bztc.domain.FlowTask;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author daism
 * @create 2024-03-27 15:58
 * @description 流程记录
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FlowTaskDto extends FlowTask {
    private static final long serialVersionUID = 7297618795878943836L;
    /**
     * 阶段号
     */
    private String phaseNo;

    /**
     * 阶段名称
     */
    private String phaseName;
    /**
     * 意见
     */
    private String opinion;

    /**
     * 审批人姓名
     */
    private String approveUserName;
}
