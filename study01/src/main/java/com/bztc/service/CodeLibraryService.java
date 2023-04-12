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

    List<Map<String, String>> queryCodeLibrary(String code);

    void freshCodeLibrary();
}
