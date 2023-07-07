package com.bztc.web.rest;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bztc.constant.Constants;
import com.bztc.domain.RuleNgParam;
import com.bztc.dto.ResultDto;
import com.bztc.service.RuleNgParamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author daism
 * @create 2023-07-06 17:01
 * @description 规则引擎参数resource
 */
@RestController
@RequestMapping("/api/rulengparamresource")
@Slf4j
public class RuleNgParamResource {
    @Autowired
    private RuleNgParamService ruleNgParamService;

    /**
     * 描述：分页查询
     *
     * @return java.util.List<com.bztc.entity.RuleNgParam>
     * @author daism
     * @date 2022-09-28 14:52:04
     */
    @GetMapping("/queryByPage")
    public ResultDto<List<RuleNgParam>> queryByPage(@RequestParam("param") String params) {
        log.info("分页查询入参：{}", params);
        JSONObject jsonObject = JSONUtil.parseObj(params);

        Page<RuleNgParam> queryPage = new Page<>((int) jsonObject.get("pageIndex"), (int) jsonObject.get("pageSize"));

        QueryWrapper<RuleNgParam> queryWrapper = new QueryWrapper<>();
        if (!StrUtil.isBlankIfStr(jsonObject.get("paramCode"))) {
            queryWrapper.like("param_Code", jsonObject.get("paramCode"));
        }
        if (!StrUtil.isBlankIfStr(jsonObject.get("paramName"))) {
            queryWrapper.like("param_Name", jsonObject.get("paramName"));
        }
        //1-生效，0-失效
        queryWrapper.eq("param_status", Constants.STATUS_EFFECT);
        queryWrapper.orderByDesc("input_time");
        Page<RuleNgParam> websiteListPage = ruleNgParamService.page(queryPage, queryWrapper);
        return new ResultDto<>(websiteListPage.getTotal(), websiteListPage.getRecords());
    }

    /**
     * 描述：编辑-更新
     *
     * @return com.bztc.dto.ResultDto<com.bztc.entity.RuleNgParam>
     * @author daism
     * @date 2022-10-14 09:55:17
     */
    @PostMapping("/update")
    public ResultDto<RuleNgParam> update(@RequestBody RuleNgParam ruleNgParam) {
        log.info("更新入参：{}", JSONUtil.toJsonStr(ruleNgParam));
        ResultDto<RuleNgParam> resultDto = new ResultDto<>();
        ruleNgParam.setUpdateUser(Constants.ADMIN);
        try {
            ruleNgParamService.updateById(ruleNgParam);
            resultDto.setCode(200);
            resultDto.setData(ruleNgParam);
        } catch (Exception e) {
            log.error("更新数据库失败！", e);
            resultDto.setCode(400);
            resultDto.setMessage("更新数据库失败," + e.getMessage());
        }
        return resultDto;
    }

    /**
     * 描述：新增
     *
     * @return com.bztc.dto.ResultDto<com.bztc.entity.RuleNgParam>
     * @author daism
     * @date 2022-09-29 10:35:10
     */
    @PostMapping("/insert")
    public ResultDto<RuleNgParam> insert(@RequestBody RuleNgParam ruleNgParam) {
        log.info("新增入参：{}", JSONUtil.toJsonStr(ruleNgParam));
        ResultDto<RuleNgParam> resultDto = new ResultDto<>();
        ruleNgParam.setParamStatus(Constants.STATUS_EFFECT);
        ruleNgParam.setInputUser(Constants.ADMIN);
        ruleNgParam.setUpdateUser(Constants.ADMIN);
        try {
            ruleNgParamService.save(ruleNgParam);
            resultDto.setCode(200);
            resultDto.setData(ruleNgParam);
        } catch (Exception e) {
            log.error("插入数据库失败！", e);
            resultDto.setCode(400);
            resultDto.setMessage("插入数据库失败," + e.getMessage());
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
    public ResultDto<Integer> delete(@RequestBody RuleNgParam ruleNgParam) {
        log.info("删除入参：{}", JSONUtil.toJsonStr(ruleNgParam));
        ResultDto<Integer> resultDto = new ResultDto<>();
        ruleNgParam.setUpdateUser(Constants.ADMIN);
        ruleNgParam.setParamCode(Constants.STATUS_NOAVAIL);
        UpdateWrapper<RuleNgParam> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", ruleNgParam.getId());
        wrapper.set("param_status", Constants.STATUS_NOAVAIL);
        try {
            ruleNgParamService.update(wrapper);
            resultDto.setCode(200);
        } catch (Exception e) {
            log.error("删除数据库失败！", e);
            resultDto.setCode(400);
            resultDto.setMessage("删除数据库失败," + e.getMessage());
        }
        return resultDto;
    }
}
