package com.bztc.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author daism
 * @create 2023-10-11 15:56
 * @description 资源dto
 */
@Data
public class AuthSourceDto implements Serializable {
    private static final long serialVersionUID = -1241670424266542791L;
    private String objectId;
    private int sourceId;
    private String sourceType;
    private String sourceName;
    private List<AuthSourceDto> children;
    private String label;
}
