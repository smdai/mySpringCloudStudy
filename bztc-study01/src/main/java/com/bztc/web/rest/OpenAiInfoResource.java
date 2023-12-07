package com.bztc.web.rest;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bztc.domain.OpenAiInfo;
import com.bztc.dto.ResultDto;
import com.bztc.service.OpenAiInfoService;
import com.bztc.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author daism
 * @create 2022-10-17 15:35
 * @description open ai controller层
 */
@RestController
@RequestMapping("/api/openaiinforesource")
@Slf4j
public class OpenAiInfoResource {
    @Autowired
    private OpenAiInfoService openAiInfoService;

    /**
     * 查询openai信息列表
     *
     * @return 用户
     */
    @GetMapping("/querybypage")
    public ResultDto<List<OpenAiInfo>> querybypage(@RequestParam("param") String params) {
        log.info("分页查询入参：{}", params);
        JSONObject jsonObject = JSONUtil.parseObj(params);

        Page<OpenAiInfo> queryPage = new Page<>((int) jsonObject.get("pageIndex"), (int) jsonObject.get("pageSize"));

        QueryWrapper<OpenAiInfo> queryWrapper = new QueryWrapper<>();
        if (!StrUtil.isBlankIfStr(jsonObject.get("input_user"))) {
            queryWrapper.eq("input_user", jsonObject.get("input_user"));
        }
        if (!StrUtil.isBlankIfStr(jsonObject.get("account"))) {
            queryWrapper.like("account", jsonObject.get("account"));
        }
        queryWrapper.eq(UserUtil.judgeAuth(), "input_user", UserUtil.getUserId());
        queryWrapper.orderByDesc("id");
        Page<OpenAiInfo> websiteListPage = this.openAiInfoService.page(queryPage, queryWrapper);
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
    public ResultDto<OpenAiInfo> insert(@RequestBody OpenAiInfo userInfo) {
        log.info("新增入参：{}", JSONUtil.toJsonStr(userInfo));
        ResultDto<OpenAiInfo> resultDto = new ResultDto<>();
        userInfo.setInputUser(UserUtil.getUserId());
        userInfo.setUpdateUser(UserUtil.getUserId());
        try {
            this.openAiInfoService.save(userInfo);
            resultDto.setCode(200);
            resultDto.setData(userInfo);
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
    public ResultDto<OpenAiInfo> update(@RequestBody OpenAiInfo userInfo) {
        log.info("更新入参：{}", JSONUtil.toJsonStr(userInfo));
        ResultDto<OpenAiInfo> resultDto = new ResultDto<>();
        userInfo.setUpdateUser(UserUtil.getUserId());
        try {
            this.openAiInfoService.updateById(userInfo);
            resultDto.setCode(200);
            resultDto.setData(userInfo);
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
    public ResultDto<Integer> delete(@RequestBody OpenAiInfo userInfo) {
        log.info("删除入参：{}", JSONUtil.toJsonStr(userInfo));
        ResultDto<Integer> resultDto = new ResultDto<>();
        try {
            this.openAiInfoService.removeById(userInfo);
            resultDto.setCode(200);
        } catch (Exception e) {
            log.error("删除数据库失败！", e);
            resultDto.setCode(400);
            resultDto.setMessage("删除数据库失败," + e.getMessage());
        }
        return resultDto;
    }
}
