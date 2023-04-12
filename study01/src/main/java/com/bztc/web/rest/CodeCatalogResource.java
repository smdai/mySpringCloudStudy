package com.bztc.web.rest;

import cn.hutool.json.JSONUtil;
import com.bztc.service.CodeCatalogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author daism
 * @create 2023-04-10 16:54
 * @description 字典目录表resource层
 */
@RestController
@RequestMapping("/bztcSystem/api/codeCatalogResource")
@Slf4j
public class CodeCatalogResource {
    @Autowired
    CodeCatalogService codeCatalogService;

    /**
     * 查询多个字典
     *
     * @param codes
     * @return
     */
    @PostMapping("/queryCodeLibraries")
    public Map<String, List<Map<String, String>>> queryCodeLibraries(@RequestBody String... codes) {
        log.info("查询多个字典入参：{}", JSONUtil.toJsonStr(codes));
        return codeCatalogService.queryCodeLibraries(codes);
    }
}
