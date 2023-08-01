package com.bztc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.domain.RuleNgParam;
import com.bztc.dto.Result;
import com.bztc.mapper.RuleNgParamMapper;
import com.bztc.service.GroovyScriptService;
import com.bztc.service.RuleNgParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author daishuming
 * @description 针对表【rule_ng_param(规则引擎参数表)】的数据库操作Service实现
 * @createDate 2023-07-06 16:24:50
 */
@Service
public class RuleNgParamServiceImpl extends ServiceImpl<RuleNgParamMapper, RuleNgParam>
        implements RuleNgParamService {
    @Autowired
    private GroovyScriptService groovyScriptService;

    @Override
    public String getResult(String jsonMsg, RuleNgParam ruleNgParam) {
        Result result = groovyScriptService.getResult(jsonMsg, ruleNgParam);
        if (result.isException()) {
            return result.getException().getLocalizedMessage();
        }
        return String.valueOf(result.getData());
    }
}




