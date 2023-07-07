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
 * 规则引擎参数表
 *
 * @author daishuming
 * @TableName rule_ng_param
 */
@TableName(value = "rule_ng_param")
@Data
public class RuleNgParam implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 参数代码,只能由字符[a-zA-Z0-9._-]构成
     */
    @TableField(value = "param_code")
    private String paramCode;
    /**
     * 参数短中文名
     */
    @TableField(value = "param_name")
    private String paramName;
    /**
     * 参数描述、说明
     */
    @TableField(value = "param_desc")
    private String paramDesc;
    /**
     * 参数类型、ClazzCode
     */
    @TableField(value = "param_type")
    private String paramType;
    /**
     * List或Map的子类型
     */
    @TableField(value = "param_sub_clazz")
    private String paramSubClazz;
    /**
     * 数据来源
     */
    @TableField(value = "ds_type")
    private String dsType;
    /**
     * 是否使用自定义脚本
     */
    @TableField(value = "customize")
    private String customize;
    /**
     * Groovy脚本
     */
    @TableField(value = "script")
    private String script;
    /**
     * 参数状态：1有效，0无效
     */
    @TableField(value = "param_status")
    private String paramStatus;
    /**
     * 脚本的hashcode
     */
    @TableField(value = "script_hash")
    private String scriptHash;
    /**
     * 参数缺省值
     */
    @TableField(value = "default_value")
    private String defaultValue;
    /**
     * 上线标签
     */
    @TableField(value = "tag")
    private String tag;
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
        RuleNgParam other = (RuleNgParam) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getParamCode() == null ? other.getParamCode() == null : this.getParamCode().equals(other.getParamCode()))
                && (this.getParamName() == null ? other.getParamName() == null : this.getParamName().equals(other.getParamName()))
                && (this.getParamDesc() == null ? other.getParamDesc() == null : this.getParamDesc().equals(other.getParamDesc()))
                && (this.getParamType() == null ? other.getParamType() == null : this.getParamType().equals(other.getParamType()))
                && (this.getParamSubClazz() == null ? other.getParamSubClazz() == null : this.getParamSubClazz().equals(other.getParamSubClazz()))
                && (this.getDsType() == null ? other.getDsType() == null : this.getDsType().equals(other.getDsType()))
                && (this.getCustomize() == null ? other.getCustomize() == null : this.getCustomize().equals(other.getCustomize()))
                && (this.getScript() == null ? other.getScript() == null : this.getScript().equals(other.getScript()))
                && (this.getParamStatus() == null ? other.getParamStatus() == null : this.getParamStatus().equals(other.getParamStatus()))
                && (this.getScriptHash() == null ? other.getScriptHash() == null : this.getScriptHash().equals(other.getScriptHash()))
                && (this.getDefaultValue() == null ? other.getDefaultValue() == null : this.getDefaultValue().equals(other.getDefaultValue()))
                && (this.getTag() == null ? other.getTag() == null : this.getTag().equals(other.getTag()))
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
        result = prime * result + ((getParamCode() == null) ? 0 : getParamCode().hashCode());
        result = prime * result + ((getParamName() == null) ? 0 : getParamName().hashCode());
        result = prime * result + ((getParamDesc() == null) ? 0 : getParamDesc().hashCode());
        result = prime * result + ((getParamType() == null) ? 0 : getParamType().hashCode());
        result = prime * result + ((getParamSubClazz() == null) ? 0 : getParamSubClazz().hashCode());
        result = prime * result + ((getDsType() == null) ? 0 : getDsType().hashCode());
        result = prime * result + ((getCustomize() == null) ? 0 : getCustomize().hashCode());
        result = prime * result + ((getScript() == null) ? 0 : getScript().hashCode());
        result = prime * result + ((getParamStatus() == null) ? 0 : getParamStatus().hashCode());
        result = prime * result + ((getScriptHash() == null) ? 0 : getScriptHash().hashCode());
        result = prime * result + ((getDefaultValue() == null) ? 0 : getDefaultValue().hashCode());
        result = prime * result + ((getTag() == null) ? 0 : getTag().hashCode());
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
        sb.append(", paramCode=").append(paramCode);
        sb.append(", paramName=").append(paramName);
        sb.append(", paramDesc=").append(paramDesc);
        sb.append(", paramType=").append(paramType);
        sb.append(", paramSubClazz=").append(paramSubClazz);
        sb.append(", dsType=").append(dsType);
        sb.append(", customize=").append(customize);
        sb.append(", script=").append(script);
        sb.append(", paramStatus=").append(paramStatus);
        sb.append(", scriptHash=").append(scriptHash);
        sb.append(", defaultValue=").append(defaultValue);
        sb.append(", tag=").append(tag);
        sb.append(", inputUser=").append(inputUser);
        sb.append(", inputTime=").append(inputTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}