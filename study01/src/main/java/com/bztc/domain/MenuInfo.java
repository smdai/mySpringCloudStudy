package com.bztc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 菜单信息表
 * @TableName menu_info
 */
@TableName(value ="menu_info")
@Data
public class MenuInfo implements Serializable {
    /**
     * 菜单id
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Integer menuId;

    /**
     * 上级菜单id
     */
    @TableField(value = "up_menu_id")
    private Integer upMenuId;

    /**
     * 菜单等级
     */
    @TableField(value = "menu_level")
    private Integer menuLevel;

    /**
     * 菜单URL
     */
    @TableField(value = "menu_url")
    private String menuUrl;

    /**
     * 菜单名称
     */
    @TableField(value = "menu_name")
    private String menuName;

    /**
     * icon
     */
    @TableField(value = "icon")
    private String icon;

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
        MenuInfo other = (MenuInfo) that;
        return (this.getMenuId() == null ? other.getMenuId() == null : this.getMenuId().equals(other.getMenuId()))
            && (this.getUpMenuId() == null ? other.getUpMenuId() == null : this.getUpMenuId().equals(other.getUpMenuId()))
            && (this.getMenuLevel() == null ? other.getMenuLevel() == null : this.getMenuLevel().equals(other.getMenuLevel()))
            && (this.getMenuUrl() == null ? other.getMenuUrl() == null : this.getMenuUrl().equals(other.getMenuUrl()))
            && (this.getMenuName() == null ? other.getMenuName() == null : this.getMenuName().equals(other.getMenuName()))
            && (this.getIcon() == null ? other.getIcon() == null : this.getIcon().equals(other.getIcon()))
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
        result = prime * result + ((getMenuId() == null) ? 0 : getMenuId().hashCode());
        result = prime * result + ((getUpMenuId() == null) ? 0 : getUpMenuId().hashCode());
        result = prime * result + ((getMenuLevel() == null) ? 0 : getMenuLevel().hashCode());
        result = prime * result + ((getMenuUrl() == null) ? 0 : getMenuUrl().hashCode());
        result = prime * result + ((getMenuName() == null) ? 0 : getMenuName().hashCode());
        result = prime * result + ((getIcon() == null) ? 0 : getIcon().hashCode());
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
        sb.append(", menuId=").append(menuId);
        sb.append(", upMenuId=").append(upMenuId);
        sb.append(", menuLevel=").append(menuLevel);
        sb.append(", menuUrl=").append(menuUrl);
        sb.append(", menuName=").append(menuName);
        sb.append(", icon=").append(icon);
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