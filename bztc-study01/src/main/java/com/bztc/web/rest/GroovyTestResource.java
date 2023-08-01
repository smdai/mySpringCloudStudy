package com.bztc.web.rest;

import cn.hutool.json.JSONUtil;
import com.bztc.dto.GroovyTestDto;
import com.bztc.dto.ResultDto;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author daism
 * @create 2022-09-25 19:43
 * @description groovy脚本测试resource层
 */
@RestController
@RequestMapping("/api/groovytestresource")
@Slf4j
public class GroovyTestResource {

    /**
     * 描述：groovy测试
     *
     * @return com.bztc.dto.ResultDto<com.bztc.entity.WebsiteList>
     * @author daism
     * @date 2022-09-29 10:35:10
     */
    @PostMapping("/groovytest")
    public ResultDto<List<Map<String, Object>>> insert(@RequestBody GroovyTestDto groovyTestDto) {
        log.info("groovy测试入参：{}", JSONUtil.toJsonStr(groovyTestDto));
        //创建GroovyShell
        GroovyShell groovyShell = new GroovyShell();
        //装载解析脚本代码
        Script script = groovyShell.parse(groovyTestDto.getGroovyScript());
        Map<String, Object> bindingMap = new HashMap<>();
        bindingMap.put("dataSource", JSONUtil.toBean(groovyTestDto.getJsonMsg(), Map.class));
        Binding binding = new Binding(bindingMap);
        script.setBinding(binding);
        Object run = script.run();
        log.info("run>>>>>>:{}", JSONUtil.toJsonStr(run));
        List<Map<String, Object>> retList = new ArrayList<>();
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("paramName", "测试参数");
        retMap.put("paramResult", run);
        retList.add(retMap);
        return new ResultDto<>(retList);
    }
}
