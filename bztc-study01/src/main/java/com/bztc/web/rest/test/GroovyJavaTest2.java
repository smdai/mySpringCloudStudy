package com.bztc.web.rest.test;

import cn.hutool.json.JSONUtil;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

/**
 * @author daism
 * @create 2023-06-28 16:14
 * @description test
 */
public class GroovyJavaTest2 {
    public static void main(String[] args) {
        //创建GroovyShell
        GroovyShell groovyShell = new GroovyShell();
        //装载解析脚本代码
        Script script = groovyShell.parse(
                "def param = { \n" +
                        " return daishuming \n" +
                        "}\n" +
                        "param.call()");
        Binding binding = new Binding();
        binding.setVariable("daishuming", "i am ok");
        binding.setVariable("language", "Groovy");
        script.setBinding(binding);
        Object run = script.run();
        System.out.println(JSONUtil.toJsonStr(run));
    }
}
