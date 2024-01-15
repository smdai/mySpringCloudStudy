package com.bztc.web.rest;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bztc.constant.Constants;
import com.bztc.domain.ImageInfo;
import com.bztc.dto.ResultDto;
import com.bztc.enumeration.FileBusinessTypeEnum;
import com.bztc.service.ImageInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author daism
 * @create 2022-10-17 15:35
 * @description 图片信息controller层
 */
@RestController
@RequestMapping("/api/imageinforesource")
@Slf4j
public class ImageInfoResource {
    @Autowired
    ImageInfoService imageInfoService;

    /**
     * 分页查询图片记录
     *
     * @return 用户
     */
    @GetMapping("/queryrecordimgbypage")
    public ResultDto<List<String>> queryRecordImgByPage(@RequestParam("param") String params) {
        log.info("分页查询入参：{}", params);
        JSONObject jsonObject = JSONUtil.parseObj(params);

        Page<ImageInfo> queryPage = new Page<>((int) jsonObject.get("pageIndex"), (int) jsonObject.get("pageSize"));

        QueryWrapper<ImageInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", FileBusinessTypeEnum.IMAGE_RECORD.key);
        queryWrapper.eq("status", Constants.STATUS_EFFECT);
        //1-生效，0-失效
        queryWrapper.orderByDesc("id");
        Page<ImageInfo> websiteListPage = this.imageInfoService.page(queryPage, queryWrapper);
        List<String> urls = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(websiteListPage.getRecords())) {
            urls = websiteListPage.getRecords().stream().map(ImageInfo::getUrl).collect(Collectors.toList());
        }
        return new ResultDto<>(websiteListPage.getTotal(), urls);
    }

}
