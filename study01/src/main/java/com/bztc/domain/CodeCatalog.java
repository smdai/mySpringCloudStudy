package com.bztc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典目录表
 *
 * @TableName code_catalog
 */
@TableName(value = "code_catalog")
@Data
public class CodeCatalog implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 数据字典目录英文值
     */
    @TableField(value = "item_catalog_code")
    private String itemCatalogCode;

    /**
     * 数据字典目录中文值
     */
    @TableField(value = "item_catalog_name")
    private String itemCatalogName;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

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
        CodeCatalog other = (CodeCatalog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getItemCatalogCode() == null ? other.getItemCatalogCode() == null : this.getItemCatalogCode().equals(other.getItemCatalogCode()))
                && (this.getItemCatalogName() == null ? other.getItemCatalogName() == null : this.getItemCatalogName().equals(other.getItemCatalogName()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
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
        result = prime * result + ((getItemCatalogCode() == null) ? 0 : getItemCatalogCode().hashCode());
        result = prime * result + ((getItemCatalogName() == null) ? 0 : getItemCatalogName().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
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
        sb.append(", itemCatalogCode=").append(itemCatalogCode);
        sb.append(", itemCatalogName=").append(itemCatalogName);
        sb.append(", remark=").append(remark);
        sb.append(", inputUser=").append(inputUser);
        sb.append(", inputTime=").append(inputTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}