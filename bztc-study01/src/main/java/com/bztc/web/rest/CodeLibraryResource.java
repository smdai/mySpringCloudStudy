package com.bztc.web.rest;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bztc.constant.Constants;
import com.bztc.domain.CodeLibrary;
import com.bztc.dto.ResultDto;
import com.bztc.service.CodeLibraryService;
import com.bztc.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author daism
 * @create 2023-04-10 16:54
 * @description 字典表resource层
 */
@RestController
@RequestMapping("/api/codelibraryresource")
@Slf4j
public class CodeLibraryResource {
    @Autowired
    CodeLibraryService codeLibraryService;

    /**
     * 查询字典列表
     *
     * @return 字典列表
     */
    @GetMapping("/querybypage")
    public ResultDto<List<CodeLibrary>> querybypage(@RequestParam("param") String params) {
        log.info("分页查询入参：{}", params);
        JSONObject jsonObject = JSONUtil.parseObj(params);

        Page<CodeLibrary> queryPage = new Page<>((int) jsonObject.get("pageIndex"), (int) jsonObject.get("pageSize"));

        QueryWrapper<CodeLibrary> queryWrapper = new QueryWrapper<>();
        if (!StrUtil.isBlankIfStr(jsonObject.get("itemCatalogCode"))) {
            queryWrapper.eq("item_catalog_code", jsonObject.get("itemCatalogCode"));
        }
        if (!StrUtil.isBlankIfStr(jsonObject.get("itemCode"))) {
            queryWrapper.like("item_code", jsonObject.get("itemCode"));
        }
        if (!StrUtil.isBlankIfStr(jsonObject.get("itemName"))) {
            queryWrapper.like("item_name", jsonObject.get("itemName"));
        }
        queryWrapper.orderByAsc(Arrays.asList("item_catalog_code", "sort_no"));
        Page<CodeLibrary> websiteListPage = this.codeLibraryService.page(queryPage, queryWrapper);
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
    public ResultDto<CodeLibrary> insert(@RequestBody CodeLibrary codeLibrary) {
        log.info("新增入参：{}", JSONUtil.toJsonStr(codeLibrary));
        ResultDto<CodeLibrary> resultDto = new ResultDto<>();
        codeLibrary.setInputUser(UserUtil.getUserId());
        codeLibrary.setUpdateUser(UserUtil.getUserId());
        codeLibrary.setStatus(Constants.STATUS_EFFECT);
        try {
            this.codeLibraryService.save(codeLibrary);
            resultDto.setCode(200);
            resultDto.setData(codeLibrary);
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
    public ResultDto<CodeLibrary> update(@RequestBody CodeLibrary codeLibrary) {
        log.info("更新入参：{}", JSONUtil.toJsonStr(codeLibrary));
        ResultDto<CodeLibrary> resultDto = new ResultDto<>();
        codeLibrary.setUpdateUser(UserUtil.getUserId());
        try {
            this.codeLibraryService.updateById(codeLibrary);
            resultDto.setCode(200);
            resultDto.setData(codeLibrary);
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
    public ResultDto<Integer> delete(@RequestBody CodeLibrary codeLibrary) {
        log.info("删除入参：{}", JSONUtil.toJsonStr(codeLibrary));
        ResultDto<Integer> resultDto = new ResultDto<>();
        try {
            this.codeLibraryService.removeById(codeLibrary);
            resultDto.setCode(200);
        } catch (Exception e) {
            log.error("删除数据库失败！", e);
            resultDto.setCode(400);
            resultDto.setMessage("删除数据库失败," + e.getMessage());
        }
        return resultDto;
    }

    /**
     * 刷新字典缓存
     *
     * @return
     */
    @GetMapping("/refreshLibrary")
    public ResultDto<Integer> refreshLibrary() {
        this.codeLibraryService.freshCodeLibrary();
        return new ResultDto<>(1);
    }
}
