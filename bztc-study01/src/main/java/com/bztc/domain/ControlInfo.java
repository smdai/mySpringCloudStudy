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
 * 控制点信息表
 *
 * @TableName control_info
 */
@TableName(value = "control_info")
@Data
public class ControlInfo implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 控制点id
     */
    @TableId(value = "control_id", type = IdType.AUTO)
    private Integer controlId;
    /**
     * 菜单id
     */
    @TableField(value = "menu_id")
    private Integer menuId;
    /**
     * 控制点键
     */
    @TableField(value = "control_key")
    private String controlKey;
    /**
     * 控制点名称
     */
    @TableField(value = "control_name")
    private String controlName;
    /**
     * 请求类型
     */
    @TableField(value = "request_type")
    private String requestType;
    /**
     * 控制点url
     */
    @TableField(value = "control_url")
    private String controlUrl;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
        ControlInfo other = (ControlInfo) that;
        return (this.getControlId() == null ? other.getControlId() == null : this.getControlId().equals(other.getControlId()))
                && (this.getMenuId() == null ? other.getMenuId() == null : this.getMenuId().equals(other.getMenuId()))
                && (this.getControlKey() == null ? other.getControlKey() == null : this.getControlKey().equals(other.getControlKey()))
                && (this.getControlName() == null ? other.getControlName() == null : this.getControlName().equals(other.getControlName()))
                && (this.getRequestType() == null ? other.getRequestType() == null : this.getRequestType().equals(other.getRequestType()))
                && (this.getControlUrl() == null ? other.getControlUrl() == null : this.getControlUrl().equals(other.getControlUrl()))
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
        result = prime * result + ((getControlId() == null) ? 0 : getControlId().hashCode());
        result = prime * result + ((getMenuId() == null) ? 0 : getMenuId().hashCode());
        result = prime * result + ((getControlKey() == null) ? 0 : getControlKey().hashCode());
        result = prime * result + ((getControlName() == null) ? 0 : getControlName().hashCode());
        result = prime * result + ((getRequestType() == null) ? 0 : getRequestType().hashCode());
        result = prime * result + ((getControlUrl() == null) ? 0 : getControlUrl().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
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
        sb.append(", controlId=").append(controlId);
        sb.append(", menuId=").append(menuId);
        sb.append(", controlKey=").append(controlKey);
        sb.append(", controlName=").append(controlName);
        sb.append(", requestType=").append(requestType);
        sb.append(", controlUrl=").append(controlUrl);
        sb.append(", status=").append(status);
        sb.append(", inputUser=").append(inputUser);
        sb.append(", inputTime=").append(inputTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}