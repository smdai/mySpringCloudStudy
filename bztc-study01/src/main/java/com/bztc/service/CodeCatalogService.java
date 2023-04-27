package com.bztc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bztc.domain.CodeCatalog;

import java.util.List;
import java.util.Map;

/**
 * @author daishuming
 * @description 针对表【code_catalog(字典目录表)】的数据库操作Service
 * @createDate 2023-04-10 14:47:45
 */
public interface CodeCatalogService extends IService<CodeCatalog> {
    /**
     * 查询数据字典
     *
     * @param codes 字典英文代码
     * @return 数据字典列表
     */
    Map<String, List<Map<String, String>>> queryCodeLibraries(String... codes);
}
