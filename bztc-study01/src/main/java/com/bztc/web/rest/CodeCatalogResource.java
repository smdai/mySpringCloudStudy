package com.bztc.web.rest;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bztc.domain.CodeCatalog;
import com.bztc.domain.CodeLibrary;
import com.bztc.dto.ResultDto;
import com.bztc.service.CodeCatalogService;
import com.bztc.service.CodeLibraryService;
import com.bztc.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author daism
 * @create 2023-04-10 16:54
 * @description 字典目录表resource层
 */
@RestController
@RequestMapping("/api/codeCatalogResource")
@Slf4j
public class CodeCatalogResource {
    @Autowired
    CodeCatalogService codeCatalogService;
    @Autowired
    CodeLibraryService codeLibraryService;

    /**
     * 查询字典目录列表
     *
     * @return 字典目录列表
     */
    @GetMapping("/querybypage")
    public ResultDto<List<CodeCatalog>> querybypage(@RequestParam("param") String params) {
        log.info("分页查询入参：{}", params);
        JSONObject jsonObject = JSONUtil.parseObj(params);

        Page<CodeCatalog> queryPage = new Page<>((int) jsonObject.get("pageIndex"), (int) jsonObject.get("pageSize"));

        QueryWrapper<CodeCatalog> queryWrapper = new QueryWrapper<>();
        if (!StrUtil.isBlankIfStr(jsonObject.get("itemCatalogCode"))) {
            queryWrapper.like("item_catalog_code", jsonObject.get("itemCatalogCode"));
        }
        if (!StrUtil.isBlankIfStr(jsonObject.get("itemCatalogName"))) {
            queryWrapper.like("item_catalog_name", jsonObject.get("itemCatalogName"));
        }
        queryWrapper.orderByDesc("id");
        Page<CodeCatalog> websiteListPage = this.codeCatalogService.page(queryPage, queryWrapper);
        return new ResultDto<>(websiteListPage.getTotal(), websiteListPage.getRecords());
    }

    /**
     * 描述：新增
     *
     * @return com.bztc.dto.ResultDto<com.bztc.entity.userInfo>
     * @author daism
     * @date 2022-09-29 10:35:10
     */
    @PostMapping("/insert")
    public ResultDto<CodeCatalog> insert(@RequestBody CodeCatalog codeCatalog) {
        log.info("新增入参：{}", JSONUtil.toJsonStr(codeCatalog));
        ResultDto<CodeCatalog> resultDto = new ResultDto<>();
        codeCatalog.setInputUser(UserUtil.getUserId());
        codeCatalog.setUpdateUser(UserUtil.getUserId());
        try {
            this.codeCatalogService.save(codeCatalog);
            resultDto.setCode(200);
            resultDto.setData(codeCatalog);
        } catch (Exception e) {
            log.error("插入数据库失败！", e);
            resultDto.setCode(400);
            resultDto.setMessage("插入数据库失败," + e.getMessage());
        }
        return resultDto;
    }

    /**
     * 描述：编辑-更新
     *
     * @return com.bztc.dto.ResultDto<com.bztc.entity.userInfo>
     * @author daism
     * @date 2022-10-14 09:55:17
     */
    @PostMapping("/update")
    public ResultDto<CodeCatalog> update(@RequestBody CodeCatalog codeCatalog) {
        log.info("更新入参：{}", JSONUtil.toJsonStr(codeCatalog));
        ResultDto<CodeCatalog> resultDto = new ResultDto<>();
        codeCatalog.setUpdateUser(UserUtil.getUserId());
        try {
            this.codeCatalogService.updateById(codeCatalog);
            resultDto.setCode(200);
            resultDto.setData(codeCatalog);
        } catch (Exception e) {
            log.error("更新数据库失败！", e);
            resultDto.setCode(400);
            resultDto.setMessage("更新数据库失败," + e.getMessage());
        }
        return resultDto;
    }

    /**
     * 描述：根据主键删除
     *
     * @return com.bztc.dto.ResultDto<java.lang.Integer>
     * @author daism
     * @date 2022-10-14 10:05:22
     */
    @PostMapping("/delete")
    @Transactional(rollbackFor = Exception.class)
    public ResultDto<Integer> delete(@RequestBody CodeCatalog codeCatalog) {
        log.info("删除入参：{}", JSONUtil.toJsonStr(codeCatalog));
        ResultDto<Integer> resultDto = new ResultDto<>();
        try {
            CodeCatalog catalog = this.codeCatalogService.getById(codeCatalog);
            QueryWrapper<CodeLibrary> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("item_catalog_code", catalog.getItemCatalogCode());
            this.codeLibraryService.remove(queryWrapper);
            this.codeCatalogService.removeById(codeCatalog);
            resultDto.setCode(200);
        } catch (Exception e) {
            log.error("删除数据库失败！", e);
            resultDto.setCode(400);
            resultDto.setMessage("删除数据库失败," + e.getMessage());
        }
        return resultDto;
    }

    /**
     * 查询多个字典
     */
    @PostMapping("/queryCodeLibraries")
    public Map<String, List<Map<String, String>>> queryCodeLibraries(@RequestBody String... codes) {
        log.info("查询多个字典入参：{}", JSONUtil.toJsonStr(codes));
        return codeCatalogService.queryCodeLibraries(codes);
    }
}
