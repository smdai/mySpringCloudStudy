package com.bztc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 流程申请表
 * @TableName flow_apply
 */
@TableName(value ="flow_apply")
@Data
public class FlowApply implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 申请类型
     */
    @TableField(value = "apply_type")
    private String applyType;

    /**
     * 对象类型
     */
    @TableField(value = "object_type")
    private String objectType;

    /**
     * 对象编号
     */
    @TableField(value = "object_no")
    private Integer objectNo;

    /**
     * 流程号
     */
    @TableField(value = "flow_no")
    private String flowNo;

    /**
     * 阶段号
     */
    @TableField(value = "phase_no")
    private String phaseNo;

    /**
     * 阶段名称
     */
    @TableField(value = "phase_name")
    private String phaseName;

    /**
     * 当前节点号
     */
    @TableField(value = "node_no")
    private String nodeNo;

    /**
     * 录入人
     */
    @TableField(value = "input_user")
    private String inputUser;

    /**
     * 录入时间
     */
    @TableField(value = "input_time")
    private Date inputTime;

    /**
     * 更新人
     */
    @TableField(value = "update_user")
    private String updateUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        FlowApply other = (FlowApply) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getApplyType() == null ? other.getApplyType() == null : this.getApplyType().equals(other.getApplyType()))
            && (this.getObjectType() == null ? other.getObjectType() == null : this.getObjectType().equals(other.getObjectType()))
            && (this.getObjectNo() == null ? other.getObjectNo() == null : this.getObjectNo().equals(other.getObjectNo()))
            && (this.getFlowNo() == null ? other.getFlowNo() == null : this.getFlowNo().equals(other.getFlowNo()))
            && (this.getPhaseNo() == null ? other.getPhaseNo() == null : this.getPhaseNo().equals(other.getPhaseNo()))
            && (this.getPhaseName() == null ? other.getPhaseName() == null : this.getPhaseName().equals(other.getPhaseName()))
            && (this.getNodeNo() == null ? other.getNodeNo() == null : this.getNodeNo().equals(other.getNodeNo()))
            && (this.getInputUser() == null ? other.getInputUser() == null : this.getInputUser().equals(other.getInputUser()))
            && (this.getInputTime() == null ? other.getInputTime() == null : this.getInputTime().equals(other.getInputTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getApplyType() == null) ? 0 : getApplyType().hashCode());
        result = prime * result + ((getObjectType() == null) ? 0 : getObjectType().hashCode());
        result = prime * result + ((getObjectNo() == null) ? 0 : getObjectNo().hashCode());
        result = prime * result + ((getFlowNo() == null) ? 0 : getFlowNo().hashCode());
        result = prime * result + ((getPhaseNo() == null) ? 0 : getPhaseNo().hashCode());
        result = prime * result + ((getPhaseName() == null) ? 0 : getPhaseName().hashCode());
        result = prime * result + ((getNodeNo() == null) ? 0 : getNodeNo().hashCode());
        result = prime * result + ((getInputUser() == null) ? 0 : getInputUser().hashCode());
        result = prime * result + ((getInputTime() == null) ? 0 : getInputTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", applyType=").append(applyType);
        sb.append(", objectType=").append(objectType);
        sb.append(", objectNo=").append(objectNo);
        sb.append(", flowNo=").append(flowNo);
        sb.append(", phaseNo=").append(phaseNo);
        sb.append(", phaseName=").append(phaseName);
        sb.append(", nodeNo=").append(nodeNo);
        sb.append(", inputUser=").append(inputUser);
        sb.append(", inputTime=").append(inputTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}