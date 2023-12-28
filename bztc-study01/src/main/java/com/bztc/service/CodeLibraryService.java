package com.bztc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bztc.domain.CodeLibrary;

import java.util.List;
import java.util.Map;

/**
 * @author daishuming
 * @description 针对表【code_library(字典详细表)】的数据库操作Service
 * @createDate 2023-04-10 14:47:45
 */
public interface CodeLibraryService extends IService<CodeLibrary> {

    /**
     * 查询单个数据字典
     *
     * @param code 字典英文值
     * @return 字典映射信息
     */
    List<Map<String, String>> queryCodeLibrary(String code);

    /**
     * 刷新全部缓存
     */
    void freshCodeLibrary();

    /**
     * 根据字典码值查询字典值
     *
     * @param catalogCode
     * @param libraryCode
     * @return
     */
    String queryValueByCode(String catalogCode, String libraryCode);
}
