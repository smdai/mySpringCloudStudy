package com.bztc.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author daism
 * @create 2023-04-07 16:25
 * @description 查询通用dto
 */
@Data
public class QueryCommonDto implements Serializable {

    private static final long serialVersionUID = -5907359837530886978L;

    /**
     * 开始时间，结束时间
     */
    private List<LocalDateTime> queryInputTime;

    /**
     * 页码
     */
    private Integer pageIndex;
    /**
     * 每页大小
     */
    private Integer pageSize;

}
