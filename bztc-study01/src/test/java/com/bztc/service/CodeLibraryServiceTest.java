package com.bztc.service;

import com.bztc.Application8001Run;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @author daism
 * @create 2023-04-10 15:05
 * @description 数据字典测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application8001Run.class)
@Slf4j
public class CodeLibraryServiceTest {
    @Autowired
    CodeLibraryService codeLibraryService;
    @Autowired
    CodeCatalogService codeCatalogService;

    @Test
    public void freshCodeLibraryTest() {
        codeLibraryService.freshCodeLibrary();
    }

    @Test
    public void queryCodeLibraryTest() {
        List<Map<String, String>> stringList = codeLibraryService.queryCodeLibrary("sex");
        System.out.println(stringList);
    }

    @Test
    public void queryCodeLibrariesTest() {
        Map<String, List<Map<String, String>>> stringList = codeCatalogService.queryCodeLibraries("sex", "status");
        System.out.println(stringList);
    }
}
