package com.bztc.dto;

import com.bztc.domain.AuthApply;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author daism
 * @create 2024-03-21 17:19
 * @description 申请查询
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthApplyDto extends AuthApply {
    private static final long serialVersionUID = 3387130425844285426L;

    /**
     * 阶段号
     */
    private String phaseNo;

}
