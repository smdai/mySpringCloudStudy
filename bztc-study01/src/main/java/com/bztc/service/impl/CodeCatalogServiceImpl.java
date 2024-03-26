package com.bztc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.constant.RedisConstants;
import com.bztc.domain.CodeCatalog;
import com.bztc.mapper.CodeCatalogMapper;
import com.bztc.service.CodeCatalogService;
import com.bztc.service.CodeLibraryService;
import com.bztc.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author daishuming
 * @description 针对表【code_catalog(字典目录表)】的数据库操作Service实现
 * @createDate 2023-04-10 14:47:45
 */
@Service
public class CodeCatalogServiceImpl extends ServiceImpl<CodeCatalogMapper, CodeCatalog> implements CodeCatalogService {
    @Autowired
    CodeLibraryService codeLibraryService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 查询数据字典
     */
    @Override
    public Map<String, List<Map<String, String>>> queryCodeLibraries(String... codes) {
        Map<String, List<Map<String, String>>> map = new HashMap<>();
        for (String code : codes) {
            if ("BztcRoles".equals(code)) {
                Object o = redisUtil.get(RedisConstants.CODE_LIBRARY_ROLE_KEY);
                if (Objects.nonNull(o)) {
                    map.put(code, (List<Map<String, String>>) o);
                }
            } else {
                List<Map<String, String>> maps = codeLibraryService.queryCodeLibrary(code);
                map.put(code, maps);
            }
        }
        return map;
    }
}




