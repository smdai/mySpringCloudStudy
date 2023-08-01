package com.bztc.dto;

import com.bztc.domain.RuleNgParam;

import java.io.Serializable;
import java.util.List;

/**
 * 规则引擎参数表+结果
 *
 * @author daishuming
 * @TableName rule_ng_param
 */
public class RuleNgParamDto extends RuleNgParam implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 参数运行结果
     */
    private String paramResult;
    /**
     * 参数编码列表
     */
    private List<String> paramCodeList;
    /**
     * json报文
     */
    private String jsonMsg;
    /**
     * 当前页码
     */
    private int pageIndex;
    /**
     * 每页大小
     */
    private int pageSize;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getJsonMsg() {
        return jsonMsg;
    }

    public void setJsonMsg(String jsonMsg) {
        this.jsonMsg = jsonMsg;
    }

    public List<String> getParamCodeList() {
        return paramCodeList;
    }

    public void setParamCodeList(List<String> paramCodeList) {
        this.paramCodeList = paramCodeList;
    }

    public String getParamResult() {
        return paramResult;
    }

    public void setParamResult(String paramResult) {
        this.paramResult = paramResult;
    }
}