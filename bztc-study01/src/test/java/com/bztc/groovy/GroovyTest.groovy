package com.bztc.groovy

import cn.hutool.json.JSONUtil
import spock.lang.Specification

class GroovyTest extends Specification {
    def dataSource
    def main = {
        def param = {
            return dataSource?.name
        }
        param.call()
    }

    def "test1"() {
        setup:
        dataSource = JSONUtil.toBean(new File(getClass().getClassLoader().getResource('json/test1.json').getFile()).text, Map.class)
        expect:
        println main()
    }
}
