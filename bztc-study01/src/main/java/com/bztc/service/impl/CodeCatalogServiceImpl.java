package com.bztc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.domain.CodeCatalog;
import com.bztc.service.CodeCatalogService;
import com.bztc.mapper.CodeCatalogMapper;
import com.bztc.service.CodeLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author daishuming
 * @description 针对表【code_catalog(字典目录表)】的数据库操作Service实现
 * @createDate 2023-04-10 14:47:45
 */
@Service
public class CodeCatalogServiceImpl extends ServiceImpl<CodeCatalogMapper, CodeCatalog> implements CodeCatalogService {
    @Autowired
    CodeLibraryService codeLibraryService;

    /**
     * 查询数据字典
     *
     * @param codes
     * @return
     */
    @Override
    public Map<String, List<Map<String, String>>> queryCodeLibraries(String... codes) {
        Map<String, List<Map<String, String>>> map = new HashMap<>();
        for (String code : codes) {
            List<Map<String, String>> maps = codeLibraryService.queryCodeLibrary(code);
            map.put(code, maps);
        }
        return map;
    }
}




