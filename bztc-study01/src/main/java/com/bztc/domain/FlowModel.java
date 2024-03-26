package com.bztc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 流程配置表
 * @TableName flow_model
 */
@TableName(value ="flow_model")
@Data
public class FlowModel implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
     * 下一节点号
     */
    @TableField(value = "next_node_no")
    private String nextNodeNo;

    /**
     * 当前节点可审批的角色
     */
    @TableField(value = "node_approve_role")
    private String nodeApproveRole;

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
        FlowModel other = (FlowModel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFlowNo() == null ? other.getFlowNo() == null : this.getFlowNo().equals(other.getFlowNo()))
            && (this.getPhaseNo() == null ? other.getPhaseNo() == null : this.getPhaseNo().equals(other.getPhaseNo()))
            && (this.getPhaseName() == null ? other.getPhaseName() == null : this.getPhaseName().equals(other.getPhaseName()))
            && (this.getNodeNo() == null ? other.getNodeNo() == null : this.getNodeNo().equals(other.getNodeNo()))
            && (this.getNextNodeNo() == null ? other.getNextNodeNo() == null : this.getNextNodeNo().equals(other.getNextNodeNo()))
            && (this.getNodeApproveRole() == null ? other.getNodeApproveRole() == null : this.getNodeApproveRole().equals(other.getNodeApproveRole()))
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
        result = prime * result + ((getFlowNo() == null) ? 0 : getFlowNo().hashCode());
        result = prime * result + ((getPhaseNo() == null) ? 0 : getPhaseNo().hashCode());
        result = prime * result + ((getPhaseName() == null) ? 0 : getPhaseName().hashCode());
        result = prime * result + ((getNodeNo() == null) ? 0 : getNodeNo().hashCode());
        result = prime * result + ((getNextNodeNo() == null) ? 0 : getNextNodeNo().hashCode());
        result = prime * result + ((getNodeApproveRole() == null) ? 0 : getNodeApproveRole().hashCode());
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
        sb.append(", flowNo=").append(flowNo);
        sb.append(", phaseNo=").append(phaseNo);
        sb.append(", phaseName=").append(phaseName);
        sb.append(", nodeNo=").append(nodeNo);
        sb.append(", nextNodeNo=").append(nextNodeNo);
        sb.append(", nodeApproveRole=").append(nodeApproveRole);
        sb.append(", inputUser=").append(inputUser);
        sb.append(", inputTime=").append(inputTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}