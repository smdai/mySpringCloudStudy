package com.bztc.service;

import com.bztc.domain.RuleNgParam;
import com.bztc.dto.GroovyResult;
import com.bztc.dto.Result;

/**
 * groovy脚本实现
 *
 * @author daishuming
 */
public interface GroovyScriptService {
    /**
     * 运行单个脚本
     *
     * @param script  脚本
     * @param jsonMsg json报文
     * @return 结果
     */
    GroovyResult execute(String jsonMsg, String script);

    /**
     * 获取参数结果
     *
     * @param jsonMsg     jsonMsg
     * @param ruleNgParam ruleNgParam
     * @return 结果
     */
    Result getResult(String jsonMsg, RuleNgParam ruleNgParam);

    /**
     * 获取参数默认值结果
     *
     * @param type        type
     * @param defultValue defultValue
     * @return 结果
     */
    Result getDefultValueResult(String type, String defultValue);

}
