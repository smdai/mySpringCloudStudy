package com.bztc.dto;

import com.bztc.domain.FlowCatalog;
import com.bztc.domain.FlowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author daism
 * @create 2024-03-26 16:34
 * @description 流程整合
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FlowCatalogModelDto extends FlowCatalog {
    private static final long serialVersionUID = -6195282040766573238L;
    private List<FlowModel> flowModelList;
}
