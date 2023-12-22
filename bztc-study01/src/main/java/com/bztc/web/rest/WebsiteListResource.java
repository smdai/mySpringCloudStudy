package com.bztc.web.rest;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bztc.constant.Constants;
import com.bztc.domain.WebsiteList;
import com.bztc.dto.ResultDto;
import com.bztc.service.WebsiteListService;
import com.bztc.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author daism
 * @create 2022-09-25 19:43
 * @description 网站地址resource层
 */
@RestController
@RequestMapping("/api/websiteListResource")
public class WebsiteListResource {
    private static final Logger logger = LoggerFactory.getLogger(WebsiteListResource.class);
    private static final String WEBSITE_URL = "websiteUrl";
    private static final String WEBSITE_NAME = "websiteName";
    private static final String TYPE = "type";
    @Autowired
    private WebsiteListService websiteListService;

    /**
     * 描述：根据主键id查询网站信息
     *
     * @return com.bztc.entity.WebsiteList
     * @author daism
     * @date 2022-09-25 20:32:44
     */
    @GetMapping("/getInfoById/{s}")
    public WebsiteList getInfoById(@PathVariable Integer s) {
        logger.info("------->/testGetFromDataBase");
        return websiteListService.getInfoById(s);
    }

    /**
     * 描述：查询所有网站列表
     *
     * @return java.util.List<com.bztc.entity.WebsiteList>
     * @author daism
     * @date 2022-09-28 10:25:22
     */
    @GetMapping("/queryAll")
    public List<WebsiteList> queryAll() {
        return websiteListService.list();
    }

    /**
     * 描述：分页查询
     *
     * @return java.util.List<com.bztc.entity.WebsiteList>
     * @author daism
     * @date 2022-09-28 14:52:04
     */
    @GetMapping("/queryByPage")
    public ResultDto<List<WebsiteList>> queryByPage(@RequestParam("param") String params) {
        logger.info("分页查询入参：{}", params);
        JSONObject jsonObject = JSONUtil.parseObj(params);

        Page<WebsiteList> queryPage = new Page<>((int) jsonObject.get("pageIndex"), (int) jsonObject.get("pageSize"));

        QueryWrapper<WebsiteList> queryWrapper = new QueryWrapper<>();
        if (!StrUtil.isBlankIfStr(jsonObject.get(WEBSITE_URL))) {
            queryWrapper.like("website_Url", jsonObject.get(WEBSITE_URL));
        }
        if (!StrUtil.isBlankIfStr(jsonObject.get(WEBSITE_NAME))) {
            queryWrapper.like("website_Name", jsonObject.get(WEBSITE_NAME));
        }
        if (!StrUtil.isBlankIfStr(jsonObject.get(TYPE))) {
            queryWrapper.eq("type", jsonObject.get(TYPE));
        }
        if ("02".equals(jsonObject.get(TYPE))) {
            queryWrapper.eq("input_user", UserUtil.getUserId());
        }
        //1-生效，0-失效
        queryWrapper.eq("status", Constants.STATUS_EFFECT);
        queryWrapper.orderByDesc("input_time");
        Page<WebsiteList> websiteListPage = websiteListService.page(queryPage, queryWrapper);
        return new ResultDto<>(websiteListPage.getTotal(), websiteListPage.getRecords());
    }

    /**
     * 描述：新增网站信息
     *
     * @return com.bztc.dto.ResultDto<com.bztc.entity.WebsiteList>
     * @author daism
     * @date 2022-09-29 10:35:10
     */
    @PostMapping("/insert")
    public ResultDto<WebsiteList> insert(@RequestBody WebsiteList websiteList) {
        logger.info("新增入参：{}", JSONUtil.toJsonStr(websiteList));
        ResultDto<WebsiteList> resultDto = new ResultDto<>();
        websiteList.setStatus(Constants.STATUS_EFFECT);
        websiteList.setInputUser(UserUtil.getUserId());
        websiteList.setUpdateUser(UserUtil.getUserId());
        try {
            websiteListService.save(websiteList);
            resultDto.setCode(200);
            resultDto.setData(websiteList);
        } catch (Exception e) {
            logger.error("插入数据库失败！", e);
            resultDto.setCode(400);
            resultDto.setMessage("插入数据库失败," + e.getMessage());
        }
        return resultDto;
    }

    /**
     * 描述：编辑-更新
     *
     * @return com.bztc.dto.ResultDto<com.bztc.entity.WebsiteList>
     * @author daism
     * @date 2022-10-14 09:55:17
     */
    @PostMapping("/update")
    public ResultDto<WebsiteList> update(@RequestBody WebsiteList websiteList) {
        logger.info("更新入参：{}", JSONUtil.toJsonStr(websiteList));
        ResultDto<WebsiteList> resultDto = new ResultDto<>();
        websiteList.setUpdateUser(UserUtil.getUserId());
        try {
            websiteListService.updateById(websiteList);
            resultDto.setCode(200);
            resultDto.setData(websiteList);
        } catch (Exception e) {
            logger.error("更新数据库失败！", e);
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
    public ResultDto<Integer> delete(@RequestBody WebsiteList websiteList) {
        logger.info("删除入参：{}", JSONUtil.toJsonStr(websiteList));
        ResultDto<Integer> resultDto = new ResultDto<>();
        websiteList.setUpdateUser(UserUtil.getUserId());
        websiteList.setStatus(Constants.STATUS_NOAVAIL);
        UpdateWrapper<WebsiteList> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", websiteList.getId());
        wrapper.set("status", Constants.STATUS_NOAVAIL);
        try {
            websiteListService.update(wrapper);
            resultDto.setCode(200);
        } catch (Exception e) {
            logger.error("删除数据库失败！", e);
            resultDto.setCode(400);
            resultDto.setMessage("删除数据库失败," + e.getMessage());
        }
        return resultDto;
    }
}
