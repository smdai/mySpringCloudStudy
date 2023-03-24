package com.bztc.dto;

import lombok.Data;

import java.util.Map;

/**
 * @author daism
 * @create 2022-09-28 14:46
 * @description 查询模型
 */
@Data
public class QueryModel {
    private Integer pageIndex;
    private Integer pageSize;
    private Map<String,Object> queryMap;
}
