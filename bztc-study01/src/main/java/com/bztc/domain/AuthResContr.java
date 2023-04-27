package com.bztc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限关联表
 *
 * @author daishuming
 * @TableName auth_res_contr
 */
@TableName(value = "auth_res_contr")
@Data
public class AuthResContr implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 关联对象类型
     */
    @TableField(value = "res_object_type")
    private String resObjectType;
    /**
     * 关联对象id
     */
    @TableField(value = "res_object_id")
    private Integer resObjectId;
    /**
     * 关联控制类型
     */
    @TableField(value = "res_contr_type")
    private String resContrType;
    /**
     * 关联控制id
     */
    @TableField(value = "res_contr_id")
    private Integer resContrId;
    /**
     * 状态
     */
    @TableField(value = "status")
    private String status;
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
        AuthResContr other = (AuthResContr) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getResObjectType() == null ? other.getResObjectType() == null : this.getResObjectType().equals(other.getResObjectType()))
                && (this.getResObjectId() == null ? other.getResObjectId() == null : this.getResObjectId().equals(other.getResObjectId()))
                && (this.getResContrType() == null ? other.getResContrType() == null : this.getResContrType().equals(other.getResContrType()))
                && (this.getResContrId() == null ? other.getResContrId() == null : this.getResContrId().equals(other.getResContrId()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
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
        result = prime * result + ((getResObjectType() == null) ? 0 : getResObjectType().hashCode());
        result = prime * result + ((getResObjectId() == null) ? 0 : getResObjectId().hashCode());
        result = prime * result + ((getResContrType() == null) ? 0 : getResContrType().hashCode());
        result = prime * result + ((getResContrId() == null) ? 0 : getResContrId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getInputUser() == null) ? 0 : getInputUser().hashCode());
        result = prime * result + ((getInputTime() == null) ? 0 : getInputTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", id=" + id +
                ", resObjectType=" + resObjectType +
                ", resObjectId=" + resObjectId +
                ", resContrType=" + resContrType +
                ", resContrId=" + resContrId +
                ", status=" + status +
                ", inputUser=" + inputUser +
                ", inputTime=" + inputTime +
                ", updateUser=" + updateUser +
                ", updateTime=" + updateTime +
                ", serialVersionUID=" + serialVersionUID +
                "]";
    }
}