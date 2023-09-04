package com.bztc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * rabbitmq记录表
 * @TableName bztc_rabbitmq_message_record
 */
@TableName(value ="bztc_rabbitmq_message_record")
@Data
public class BztcRabbitmqMessageRecord implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 方法类型
     */
    @TableField(value = "method_type")
    private String methodType;

    /**
     * mq消息
     */
    @TableField(value = "message")
    private String message;

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
        BztcRabbitmqMessageRecord other = (BztcRabbitmqMessageRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMethodType() == null ? other.getMethodType() == null : this.getMethodType().equals(other.getMethodType()))
            && (this.getMessage() == null ? other.getMessage() == null : this.getMessage().equals(other.getMessage()))
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
        result = prime * result + ((getMethodType() == null) ? 0 : getMethodType().hashCode());
        result = prime * result + ((getMessage() == null) ? 0 : getMessage().hashCode());
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
        sb.append(", methodType=").append(methodType);
        sb.append(", message=").append(message);
        sb.append(", inputUser=").append(inputUser);
        sb.append(", inputTime=").append(inputTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}