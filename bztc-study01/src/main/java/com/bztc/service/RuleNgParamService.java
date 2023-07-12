package com.bztc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bztc.domain.RuleNgParam;

/**
 * @author daishuming
 * @description 针对表【rule_ng_param(规则引擎参数表)】的数据库操作Service
 * @createDate 2023-07-06 16:24:50
 */
public interface RuleNgParamService extends IService<RuleNgParam> {
    String getResult(String jsonMsg, RuleNgParam ruleNgParam);
}
