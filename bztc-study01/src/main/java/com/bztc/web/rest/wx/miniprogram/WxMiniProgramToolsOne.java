package com.bztc.web.rest.wx.miniprogram;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.bztc.constant.RedisConstants;
import com.bztc.domain.OutApiResponseRecord;
import com.bztc.dto.ResultDto;
import com.bztc.enumeration.OutApiHttpUrlEnum;
import com.bztc.service.OutApiResponseRecordService;
import com.bztc.utils.RedisUtil;
import com.bztc.utils.YamlPropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author daism
 * @create 2024-03-07 15:25
 * @description 微信小程序-工具相关1
 */
@RestController
@RequestMapping("/api/wx/miniprogram/wxminiprogramtoolsone")
@Slf4j
public class WxMiniProgramToolsOne {
    @Autowired
    private OutApiResponseRecordService outApiResponseRecordService;
    @Autowired
    private RedisUtil redisUtil;
    @Value("${out.api.key.tanshu}")
    private String tanshuApiKey;

    /**
     * yml转properties
     *
     * @param yamlText
     * @return
     */
    @PostMapping("/convertyamltoproperties")
    public ResultDto<String> convertYamlToProperties(@RequestBody String yamlText) {
        return new ResultDto<>(YamlPropertiesUtil.castToProperties(yamlText));
    }

    /**
     * properties转yml
     *
     * @param propertiesText
     * @return
     */
    @PostMapping("/convertpropertiestoyaml")
    public ResultDto<String> convertPropertiesToYaml(@RequestBody String propertiesText) {
        return new ResultDto<>(YamlPropertiesUtil.castToYaml(propertiesText));
    }

    /**
     * 查询黄金数据
     *
     * @param type
     * @return
     */
    @GetMapping("/querygolddata")
    public ResultDto<List> queryGoldData(@RequestParam("type") String type) {
        //获取redis
        Object o = redisUtil.get(RedisConstants.OUT_API_PREFIX + OutApiHttpUrlEnum.TANSHU_GOLD_DATA.name + ":" + type);
        if (Objects.nonNull(o)) {
            Map bodyMap = JSONUtil.toBean(String.valueOf(o), Map.class);
            Object data = bodyMap.get("data");
            Map dataMap = JSONUtil.toBean(String.valueOf(data), Map.class);
            return new ResultDto<>((List) dataMap.get("list"));
        }
        HttpRequest httpRequest = HttpRequest.get(String.format(OutApiHttpUrlEnum.TANSHU_GOLD_DATA.url, type, tanshuApiKey));

        String body;
        try (HttpResponse execute = httpRequest.execute()) {
            body = execute.body();
        } catch (Exception e) {
            log.error("调{}失败。", OutApiHttpUrlEnum.TANSHU_GOLD_DATA.name, e);
            return new ResultDto<>(400, "调外部api失败。");
        }
        redisUtil.set(RedisConstants.OUT_API_PREFIX + OutApiHttpUrlEnum.TANSHU_GOLD_DATA.name + ":" + type, body, 14400);
        //入表
        OutApiResponseRecord outApiResponseRecord = new OutApiResponseRecord();
        outApiResponseRecord.setApiType(OutApiHttpUrlEnum.TANSHU_GOLD_DATA.name);
        outApiResponseRecord.setCallType1(type);
        outApiResponseRecord.setResponse(body);
        outApiResponseRecordService.save(outApiResponseRecord);
        try {
            Map bodyMap = JSONUtil.toBean(body, Map.class);
            Object data = bodyMap.get("data");
            Map dataMap = JSONUtil.toBean(String.valueOf(data), Map.class);
            return new ResultDto<>((List) dataMap.get("list"));
        } catch (Exception e) {
            log.error("解析返回报文失败。{}", body, e);
            return new ResultDto<>(400, "解析返回报文失败。");
        }
    }

    /**
     * 查询白银数据
     *
     * @param type
     * @return
     */
    @GetMapping("/querysilverdata")
    public ResultDto<List> querySilverData(@RequestParam("type") String type) {
        //获取redis
        Object o = redisUtil.get(RedisConstants.OUT_API_PREFIX + OutApiHttpUrlEnum.TANSHU_SILVER_DATA.name + ":" + type);
        if (Objects.nonNull(o)) {
            Map bodyMap = JSONUtil.toBean(String.valueOf(o), Map.class);
            Object data = bodyMap.get("data");
            Map dataMap = JSONUtil.toBean(String.valueOf(data), Map.class);
            return new ResultDto<>((List) dataMap.get("list"));
        }
        HttpRequest httpRequest = HttpRequest.get(String.format(OutApiHttpUrlEnum.TANSHU_SILVER_DATA.url, type, tanshuApiKey));

        String body;
        try (HttpResponse execute = httpRequest.execute()) {
            body = execute.body();
        } catch (Exception e) {
            log.error("调{}失败。", OutApiHttpUrlEnum.TANSHU_SILVER_DATA.name, e);
            return new ResultDto<>(400, "调外部api失败。");
        }
        redisUtil.set(RedisConstants.OUT_API_PREFIX + OutApiHttpUrlEnum.TANSHU_SILVER_DATA.name + ":" + type, body, 14400);
        //入表
        OutApiResponseRecord outApiResponseRecord = new OutApiResponseRecord();
        outApiResponseRecord.setApiType(OutApiHttpUrlEnum.TANSHU_SILVER_DATA.name);
        outApiResponseRecord.setCallType1(type);
        outApiResponseRecord.setResponse(body);
        outApiResponseRecordService.save(outApiResponseRecord);
        try {
            Map bodyMap = JSONUtil.toBean(body, Map.class);
            Object data = bodyMap.get("data");
            Map dataMap = JSONUtil.toBean(String.valueOf(data), Map.class);
            return new ResultDto<>((List) dataMap.get("list"));
        } catch (Exception e) {
            log.error("解析返回报文失败。{}", body, e);
            return new ResultDto<>(400, "解析返回报文失败。");
        }
    }
}
