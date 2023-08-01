package com.bztc.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.bztc.domain.RuleNgParam;
import com.bztc.dto.GroovyResult;
import com.bztc.dto.Result;
import com.bztc.enumeration.ParamTypeEnum;
import com.bztc.service.GroovyScriptService;
import com.bztc.utils.DateUtil;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author daism
 * @create 2023-07-11 09:32
 * @description 脚本运行实现类
 */
@Slf4j
@Service
public class GroovyScriptServiceImpl implements GroovyScriptService {
    /**
     * 运行单个脚本
     *
     * @param scriptStr 脚本
     * @param jsonMsg   json报文
     * @return 结果
     */
    @Override
    public GroovyResult execute(String jsonMsg, String scriptStr) {
        try {
            //创建GroovyShell
            GroovyShell groovyShell = new GroovyShell();
            Script script = groovyShell.parse(scriptStr);
            //装载解析脚本代码
            Map<String, Object> bindingMap = new HashMap<>();
            bindingMap.put("dataSource", JSONUtil.toBean(jsonMsg, Map.class));
            Binding binding = new Binding(bindingMap);
            binding.setVariable("language", "Groovy");
            script.setBinding(binding);
            Object result = script.run();
            return GroovyResult.newGroovyResult(false, result);
        } catch (Throwable e) {
            log.error("运行单个脚本异常.", e);
            return GroovyResult.newGroovyResult(e, null);
        }
    }

    /**
     * 获取参数结果
     *
     * @param jsonMsg     jsonMsg
     * @param ruleNgParam ruleNgParam
     * @return 结果
     */
    @Override
    public Result getResult(String jsonMsg, RuleNgParam ruleNgParam) {
        Result result = Result.emptyResult();
        try {
            //如果参数脚本为空，则直接分会默认值
            if (StrUtil.isBlankIfStr(ruleNgParam.getScript())) {
                result = getDefultValueResult(ruleNgParam.getParamType(), ruleNgParam.getDefaultValue());
                log.info("groovy脚本为空。");
                return result;
            }
            GroovyResult groovyResult = this.execute(jsonMsg, ruleNgParam.getScript());
            //如果数据源异常，直接返回默认值
            if (groovyResult.isException()) {
                result = getDefultValueResult(ruleNgParam.getParamType(), ruleNgParam.getDefaultValue());
                result.setException(groovyResult.getException());
                log.info("groovy脚本运行异常。");
                return result;
            }
            //获取脚本执行结果
            Object obj = groovyResult.getObj();
            //获取参数类型
            String type = ruleNgParam.getParamType();
            if (StrUtil.isBlankIfStr(type)) {
                type = StringUtils.EMPTY;
            }
            if (ParamTypeEnum.INT.name().equals(type) || ParamTypeEnum.NUMBER.name().equals(type)) {
                BigDecimal num = StrUtil.isBlankIfStr(obj) ? BigDecimal.ZERO : new BigDecimal(obj.toString());
                result = Result.newResult(num, BigDecimal.class);
            } else if (ParamTypeEnum.STRING.name().equals(type)) {
                result = Result.newResult(String.valueOf(obj), String.class);
            } else if (ParamTypeEnum.BOOLEAN.name().equals(type)) {
                result = Result.newResult(Boolean.valueOf(obj.toString()), Boolean.class);
            } else if (ParamTypeEnum.LIST.name().equals(type)) {
                result = Result.newResult((List) obj, List.class);
            } else if (ParamTypeEnum.MAP.name().equals(type)) {
                result = Result.newResult((Map) obj, Map.class);
            } else if (ParamTypeEnum.DATE.name().equals(type)) {
                LocalDate localDate = null;
                if (StrUtil.isBlankIfStr(obj)) {
                    localDate = DateUtil.getLocalDate(obj.toString(), DateUtil.PATTERN_YYYY_MM_DD_HH_MM_SS);
                }
                result = Result.newResult(localDate, LocalDate.class);
            }
        } catch (Throwable e) {
            log.error("参数运行异常：{}", ruleNgParam.getParamCode(), e);
            result = getDefultValueResult(ruleNgParam.getParamType(), ruleNgParam.getDefaultValue());
            result.setException(e);
        } finally {
            log.info("参数运行结束。参数代码：{},参数名：{},结果：{}", ruleNgParam.getParamCode(), ruleNgParam.getParamName(), JSONUtil.toJsonStr(result));
        }
        return result;
    }

    /**
     * 获取参数默认值结果
     *
     * @param type        type
     * @param defultValue defultValue
     * @return 结果
     */
    @Override
    public Result getDefultValueResult(String type, String defultValue) {
        if (StrUtil.isBlankIfStr(defultValue)) {
            if (ParamTypeEnum.INT.name().equals(type) || ParamTypeEnum.NUMBER.name().equals(type)) {
                return Result.newResult(BigDecimal.ZERO, BigDecimal.class);
            } else if (ParamTypeEnum.STRING.name().equals(type)) {
                return Result.newResult(StringUtils.EMPTY, String.class);
            } else if (ParamTypeEnum.BOOLEAN.name().equals(type)) {
                return Result.newResult(Boolean.FALSE, Boolean.class);
            } else if (ParamTypeEnum.LIST.name().equals(type)) {
                return Result.newResult(new ArrayList<>(), List.class);
            } else if (ParamTypeEnum.MAP.name().equals(type)) {
                return Result.newResult(new HashMap<>(), Map.class);
            }
        } else {
            if (ParamTypeEnum.INT.name().equals(type) || ParamTypeEnum.NUMBER.name().equals(type)) {
                return Result.newResult(new BigDecimal(defultValue), BigDecimal.class);
            } else if (ParamTypeEnum.STRING.name().equals(type)) {
                return Result.newResult(defultValue, String.class);
            } else if (ParamTypeEnum.BOOLEAN.name().equals(type)) {
                return Result.newResult(Boolean.valueOf(defultValue), Boolean.class);
            } else if (ParamTypeEnum.LIST.name().equals(type)) {
                List<String> list = new ArrayList<>();
                list.add(defultValue);
                return Result.newResult(list, List.class);
            } else if (ParamTypeEnum.MAP.name().equals(type)) {
                return Result.newResult(new HashMap<>(), Map.class);
            }
        }
        return Result.emptyResult();
    }
}
