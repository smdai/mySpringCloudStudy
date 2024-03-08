package com.bztc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 外部API接口响应记录表
 * @TableName out_api_response_record
 */
@TableName(value ="out_api_response_record")
@Data
public class OutApiResponseRecord implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 接口类型
     */
    @TableField(value = "api_type")
    private String apiType;

    /**
     * 接口调用类型1
     */
    @TableField(value = "call_type1")
    private String callType1;

    /**
     * 接口调用类型2
     */
    @TableField(value = "call_type2")
    private String callType2;

    /**
     * 接口调用类型3
     */
    @TableField(value = "call_type3")
    private String callType3;

    /**
     * 接口调用类型4
     */
    @TableField(value = "call_type4")
    private String callType4;

    /**
     * 接口调用类型5
     */
    @TableField(value = "call_type5")
    private String callType5;

    /**
     * 接口响应报文
     */
    @TableField(value = "response")
    private String response;

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
        OutApiResponseRecord other = (OutApiResponseRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getApiType() == null ? other.getApiType() == null : this.getApiType().equals(other.getApiType()))
            && (this.getCallType1() == null ? other.getCallType1() == null : this.getCallType1().equals(other.getCallType1()))
            && (this.getCallType2() == null ? other.getCallType2() == null : this.getCallType2().equals(other.getCallType2()))
            && (this.getCallType3() == null ? other.getCallType3() == null : this.getCallType3().equals(other.getCallType3()))
            && (this.getCallType4() == null ? other.getCallType4() == null : this.getCallType4().equals(other.getCallType4()))
            && (this.getCallType5() == null ? other.getCallType5() == null : this.getCallType5().equals(other.getCallType5()))
            && (this.getResponse() == null ? other.getResponse() == null : this.getResponse().equals(other.getResponse()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getApiType() == null) ? 0 : getApiType().hashCode());
        result = prime * result + ((getCallType1() == null) ? 0 : getCallType1().hashCode());
        result = prime * result + ((getCallType2() == null) ? 0 : getCallType2().hashCode());
        result = prime * result + ((getCallType3() == null) ? 0 : getCallType3().hashCode());
        result = prime * result + ((getCallType4() == null) ? 0 : getCallType4().hashCode());
        result = prime * result + ((getCallType5() == null) ? 0 : getCallType5().hashCode());
        result = prime * result + ((getResponse() == null) ? 0 : getResponse().hashCode());
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
        sb.append(", apiType=").append(apiType);
        sb.append(", callType1=").append(callType1);
        sb.append(", callType2=").append(callType2);
        sb.append(", callType3=").append(callType3);
        sb.append(", callType4=").append(callType4);
        sb.append(", callType5=").append(callType5);
        sb.append(", response=").append(response);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}