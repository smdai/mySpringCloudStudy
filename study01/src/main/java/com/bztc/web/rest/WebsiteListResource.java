package com.bztc.web.rest;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bztc.constant.Constants;
import com.bztc.dto.ResultDto;
import com.bztc.entity.WebsiteList;
import com.bztc.service.WebsiteListService;
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
    @Autowired
    private WebsiteListService websiteListService;

    /*
     * 描述：根据主键id查询网站信息
     * @author daism
     * @date 2022-09-25 20:32:44
     * @param s
     * @return com.bztc.entity.WebsiteList
     */
    @GetMapping("/getInfoById/{s}")
    public WebsiteList getInfoById(@PathVariable Integer s) {
        logger.info("------->/testGetFromDataBase");
        return websiteListService.getInfoById(s);
    }

    /*
     * 描述：查询所有网站列表
     * @author daism
     * @date 2022-09-28 10:25:22
     * @param
     * @return java.util.List<com.bztc.entity.WebsiteList>
     */
    @GetMapping("/queryAll")
    public List<WebsiteList> queryAll() {
        return websiteListService.list();
    }

    /*
     * 描述：分页查询
     * @author daism
     * @date 2022-09-28 14:52:04
     * @param
     * @return java.util.List<com.bztc.entity.WebsiteList>
     */
    @GetMapping("/queryByPage")
    public ResultDto<List<WebsiteList>> queryByPage(@RequestParam("param") String params) {
        logger.info("分页查询入参：{}",params);
        JSONObject jsonObject = JSONUtil.parseObj(params);

        Page<WebsiteList> queryPage = new Page<>((int)jsonObject.get("pageIndex"), (int)jsonObject.get("pageSize"));

        QueryWrapper<WebsiteList> queryWrapper = new QueryWrapper<>();
        if(!StrUtil.isBlankIfStr(jsonObject.get("websiteUrl"))){
            queryWrapper.like("website_Url",jsonObject.get("websiteUrl"));
        }
        if(!StrUtil.isBlankIfStr(jsonObject.get("websiteName"))){
            queryWrapper.like("website_Name",jsonObject.get("websiteName"));
        }
        if(!StrUtil.isBlankIfStr(jsonObject.get("type"))){
            queryWrapper.eq("type",jsonObject.get("type"));
        }
        queryWrapper.eq("status",Constants.STATUS_EFFECT);//1-生效，0-失效
        queryWrapper.orderByDesc("input_time");
        Page<WebsiteList> websiteListPage = websiteListService.page(queryPage,queryWrapper);
        return new ResultDto<>(websiteListPage.getTotal(),websiteListPage.getRecords());
    }
    /*
     * 描述：新增网站信息
     * @author daism
     * @date 2022-09-29 10:35:10
     * @param websiteList
     * @return com.bztc.dto.ResultDto<com.bztc.entity.WebsiteList>
     */
    @PostMapping("/insert")
    public ResultDto<WebsiteList> insert(@RequestBody WebsiteList websiteList){
        logger.info("新增入参：{}",JSONUtil.toJsonStr(websiteList));
        ResultDto<WebsiteList> resultDto = new ResultDto<>();
        websiteList.setStatus(Constants.STATUS_EFFECT);
        websiteList.setInputUser(Constants.ADMIN);
        websiteList.setUpdateUser(Constants.ADMIN);
        try {
            websiteListService.save(websiteList);
            resultDto.setCode(200);
            resultDto.setData(websiteList);
        }catch (Exception e){
            logger.error("插入数据库失败！",e);
            resultDto.setCode(400);
            resultDto.setMessage("插入数据库失败,"+e.getMessage());
        }
        return resultDto;
    }
    /*
     * 描述：编辑-更新
     * @author daism
     * @date 2022-10-14 09:55:17
     * @param websiteList
     * @return com.bztc.dto.ResultDto<com.bztc.entity.WebsiteList>
     */
    @PostMapping("/update")
    public ResultDto<WebsiteList> update(@RequestBody WebsiteList websiteList){
        logger.info("更新入参：{}",JSONUtil.toJsonStr(websiteList));
        ResultDto<WebsiteList> resultDto = new ResultDto<>();
        websiteList.setUpdateUser(Constants.ADMIN);
        try {
            websiteListService.updateById(websiteList);
            resultDto.setCode(200);
            resultDto.setData(websiteList);
        }catch (Exception e){
            logger.error("更新数据库失败！",e);
            resultDto.setCode(400);
            resultDto.setMessage("更新数据库失败,"+e.getMessage());
        }
        return resultDto;
    }
    /*
     * 描述：根据主键删除
     * @author daism
     * @date 2022-10-14 10:05:22
     * @param websiteList
     * @return com.bztc.dto.ResultDto<java.lang.Integer>
     */
    @PostMapping("/delete")
    public ResultDto<Integer> delete(@RequestBody WebsiteList websiteList){
        logger.info("删除入参：{}",JSONUtil.toJsonStr(websiteList));
        ResultDto<Integer> resultDto = new ResultDto<>();
        websiteList.setUpdateUser(Constants.ADMIN);
        websiteList.setStatus(Constants.STATUS_NOAVAIL);
        UpdateWrapper<WebsiteList> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",websiteList.getId());
        wrapper.set("status",Constants.STATUS_NOAVAIL);
        try {
            websiteListService.update(wrapper);
            resultDto.setCode(200);
        }catch (Exception e){
            logger.error("删除数据库失败！",e);
            resultDto.setCode(400);
            resultDto.setMessage("删除数据库失败,"+e.getMessage());
        }
        return resultDto;
    }
}
