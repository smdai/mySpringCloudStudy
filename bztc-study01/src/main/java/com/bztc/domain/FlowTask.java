package com.bztc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程任务表
 *
 * @TableName flow_task
 */
@TableName(value = "flow_task")
@Data
public class FlowTask implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
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
     * 当前节点号
     */
    @TableField(value = "node_no")
    private String nodeNo;
    /**
     * 当前节点审批人
     */
    @TableField(value = "node_approve_user")
    private Integer nodeApproveUser;
    /**
     * 审批开始时间
     */
    @TableField(value = "begin_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    /**
     * 审批结束时间
     */
    @TableField(value = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

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
        FlowTask other = (FlowTask) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getObjectType() == null ? other.getObjectType() == null : this.getObjectType().equals(other.getObjectType()))
                && (this.getObjectNo() == null ? other.getObjectNo() == null : this.getObjectNo().equals(other.getObjectNo()))
                && (this.getNodeNo() == null ? other.getNodeNo() == null : this.getNodeNo().equals(other.getNodeNo()))
                && (this.getNodeApproveUser() == null ? other.getNodeApproveUser() == null : this.getNodeApproveUser().equals(other.getNodeApproveUser()))
                && (this.getBeginTime() == null ? other.getBeginTime() == null : this.getBeginTime().equals(other.getBeginTime()))
                && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getObjectType() == null) ? 0 : getObjectType().hashCode());
        result = prime * result + ((getObjectNo() == null) ? 0 : getObjectNo().hashCode());
        result = prime * result + ((getNodeNo() == null) ? 0 : getNodeNo().hashCode());
        result = prime * result + ((getNodeApproveUser() == null) ? 0 : getNodeApproveUser().hashCode());
        result = prime * result + ((getBeginTime() == null) ? 0 : getBeginTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", objectType=").append(objectType);
        sb.append(", objectNo=").append(objectNo);
        sb.append(", nodeNo=").append(nodeNo);
        sb.append(", nodeApproveUser=").append(nodeApproveUser);
        sb.append(", beginTime=").append(beginTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}