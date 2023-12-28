package com.bztc.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.constant.Constants;
import com.bztc.constant.RedisConstants;
import com.bztc.domain.CodeLibrary;
import com.bztc.mapper.CodeLibraryMapper;
import com.bztc.service.CodeLibraryService;
import com.bztc.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author daishuming
 * @description 针对表【code_library(字典详细表)】的数据库操作Service实现
 * @createDate 2023-04-10 14:47:45
 */
@Service
public class CodeLibraryServiceImpl extends ServiceImpl<CodeLibraryMapper, CodeLibrary> implements CodeLibraryService {
    @Autowired
    RedisUtil redisUtil;

    /**
     * 查询单个数据字典
     */
    @Override
    @Cacheable(value = RedisConstants.CODE_LIBRARY_ITEM_KEY, key = "#code")
    public List<Map<String, String>> queryCodeLibrary(String code) {
        QueryWrapper<CodeLibrary> codeLibraryQueryWrapper = new QueryWrapper<>();
        codeLibraryQueryWrapper.eq("status", Constants.STATUS_EFFECT);
        codeLibraryQueryWrapper.eq("item_Catalog_Code", code);
        codeLibraryQueryWrapper.orderByAsc("sort_no");
        List<CodeLibrary> codeLibraries = this.list(codeLibraryQueryWrapper);
        if (CollectionUtil.isEmpty(codeLibraries)) {
            return null;
        }
        return codeLibraries.stream().map(it -> {
            Map<String, String> map = new HashMap<>();
            map.put("key", it.getItemCode());
            map.put("value", it.getItemName());
            return map;
        }).collect(Collectors.toList());
    }

    /**
     * 刷新全部缓存
     */
    @Override
    public void freshCodeLibrary() {
        redisUtil.delAll(RedisConstants.CODE_LIBRARY_ITEM_KEY);
        QueryWrapper<CodeLibrary> codeLibraryQueryWrapper = new QueryWrapper<>();
        codeLibraryQueryWrapper.eq("status", Constants.STATUS_EFFECT);
        codeLibraryQueryWrapper.orderByAsc("sort_no");
        List<CodeLibrary> codeLibraries = this.list(codeLibraryQueryWrapper);
        List<Map<String, String>> mapList = codeLibraries.stream().map(it -> {
            Map<String, String> map = new HashMap<>();
            map.put("catalog", it.getItemCatalogCode());
            map.put("key", it.getItemCode());
            map.put("value", it.getItemName());
            return map;
        }).collect(Collectors.toList());
        Map<String, List<Map<String, String>>> codeLibraryListMap = mapList.stream().collect(Collectors.groupingBy(it -> it.get("catalog")));
        codeLibraryListMap.forEach((k, v) -> {
            List<Map<String, String>> collects = v.stream().peek(it -> it.remove("catalog")).collect(Collectors.toList());
            redisUtil.set(RedisConstants.CODE_LIBRARY_ITEM_KEY + ":" + k, collects);
        });
    }

    /**
     * 根据字典码值查询字典值
     *
     * @param catalogCode
     * @param libraryCode
     * @return
     */
    @Override
    public String queryValueByCode(String catalogCode, String libraryCode) {
        List<Map<String, String>> libraryList = this.queryCodeLibrary(catalogCode);
        for (Map<String, String> library : libraryList) {
            String key = library.get("key");
            if (libraryCode.equals(key)) {
                return library.get("value");
            }
        }
        return StringUtils.EMPTY;
    }
}




