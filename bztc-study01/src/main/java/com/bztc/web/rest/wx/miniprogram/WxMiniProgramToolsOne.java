package com.bztc.web.rest.wx.miniprogram;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import com.bztc.constant.RedisConstants;
import com.bztc.domain.OutApiResponseRecord;
import com.bztc.dto.ResultDto;
import com.bztc.enumeration.OutApiHttpUrlEnum;
import com.bztc.service.OutApiResponseRecordService;
import com.bztc.utils.*;
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

    /**
     * 查询油价
     *
     * @return
     */
    @GetMapping("/queryoilprice")
    public ResultDto<List> queryOilPrice() {
        //获取当前日期
        String nowDateStr = DateUtil.getNowTimeStr("yyyyMMdd");
        //获取redis
        Object o = redisUtil.get(RedisConstants.OUT_API_PREFIX + OutApiHttpUrlEnum.TANSHU_OIL_PRICE_DATA.name + ":" + nowDateStr);
        if (Objects.nonNull(o)) {
            Map bodyMap = JSONUtil.toBean(String.valueOf(o), Map.class);
            Object data = bodyMap.get("data");
            Map dataMap = JSONUtil.toBean(String.valueOf(data), Map.class);
            return new ResultDto<>((List) dataMap.get("list"));
        }
        HttpRequest httpRequest = HttpRequest.get(String.format(OutApiHttpUrlEnum.TANSHU_OIL_PRICE_DATA.url, tanshuApiKey));

        String body;
        try (HttpResponse execute = httpRequest.execute()) {
            body = execute.body();
        } catch (Exception e) {
            log.error("调{}失败。", OutApiHttpUrlEnum.TANSHU_OIL_PRICE_DATA.name, e);
            return new ResultDto<>(400, "调外部api失败。");
        }
        redisUtil.set(RedisConstants.OUT_API_PREFIX + OutApiHttpUrlEnum.TANSHU_OIL_PRICE_DATA.name + ":" + nowDateStr, body, 24 * 60 * 60);
        //入表
        OutApiResponseRecord outApiResponseRecord = new OutApiResponseRecord();
        outApiResponseRecord.setApiType(OutApiHttpUrlEnum.TANSHU_OIL_PRICE_DATA.name);
        outApiResponseRecord.setCallType1(nowDateStr);
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
     * 查询全球ip地址
     *
     * @return
     */
    @GetMapping("/queryglobalipaddress")
    public ResultDto<Map> queryGlobalIpAddress(@RequestParam("ip") String ip) {
        if (!NetWorkUtil.isValidIpAddress(ip)) {
            return new ResultDto<>(400, "不是有效ip");
        }
        //获取redis
        Object o = redisUtil.get(RedisConstants.OUT_API_PREFIX + OutApiHttpUrlEnum.TANSHU_GLOBAL_IP_ADDRESS_DATA.name + ":" + ip);
        if (Objects.nonNull(o)) {
            Map bodyMap = JSONUtil.toBean(String.valueOf(o), Map.class);
            Object data = bodyMap.get("data");
            Map dataMap = JSONUtil.toBean(String.valueOf(data), Map.class);
            return new ResultDto<>(dataMap);
        }
        HttpRequest httpRequest = HttpRequest.get(String.format(OutApiHttpUrlEnum.TANSHU_GLOBAL_IP_ADDRESS_DATA.url, tanshuApiKey, ip));

        String body;
        try (HttpResponse execute = httpRequest.execute()) {
            body = execute.body();
        } catch (Exception e) {
            log.error("调{}失败。", OutApiHttpUrlEnum.TANSHU_GLOBAL_IP_ADDRESS_DATA.name, e);
            return new ResultDto<>(400, "调外部api失败。");
        }
        redisUtil.set(RedisConstants.OUT_API_PREFIX + OutApiHttpUrlEnum.TANSHU_GLOBAL_IP_ADDRESS_DATA.name + ":" + ip, body, 6 * 60 * 60);
        try {
            Map bodyMap = JSONUtil.toBean(body, Map.class);
            Object data = bodyMap.get("data");
            Map dataMap = JSONUtil.toBean(String.valueOf(data), Map.class);
            return new ResultDto<>(dataMap);
        } catch (Exception e) {
            log.error("解析返回报文失败。{}", body, e);
            return new ResultDto<>(400, "解析返回报文失败。");
        }
    }

    /**
     * 查询手机归属地
     *
     * @return
     */
    @GetMapping("/queryphoneownership")
    public ResultDto<Map> queryPhoneOwnership(@RequestParam("phoneNo") String phoneNo) {
        if (!CommonUtil.isValidPhoneNumber(phoneNo)) {
            return new ResultDto<>(400, "不是有效手机号");
        }
        //获取redis
        Object o = redisUtil.get(RedisConstants.OUT_API_PREFIX + OutApiHttpUrlEnum.TANSHU_PHONE_OWNERSHIP_DATA.name + ":" + phoneNo);
        if (Objects.nonNull(o)) {
            Map bodyMap = JSONUtil.toBean(String.valueOf(o), Map.class);
            Object data = bodyMap.get("data");
            Map dataMap = JSONUtil.toBean(String.valueOf(data), Map.class);
            return new ResultDto<>(dataMap);
        }
        HttpRequest httpRequest = HttpRequest.get(String.format(OutApiHttpUrlEnum.TANSHU_PHONE_OWNERSHIP_DATA.url, tanshuApiKey, phoneNo));
        String body;
        try (HttpResponse execute = httpRequest.execute()) {
            body = execute.body();
        } catch (Exception e) {
            log.error("调{}失败。", OutApiHttpUrlEnum.TANSHU_PHONE_OWNERSHIP_DATA.name, e);
            return new ResultDto<>(400, "调外部api失败。");
        }
        redisUtil.set(RedisConstants.OUT_API_PREFIX + OutApiHttpUrlEnum.TANSHU_PHONE_OWNERSHIP_DATA.name + ":" + phoneNo, body, 24 * 60 * 60);
        //入表
        OutApiResponseRecord outApiResponseRecord = new OutApiResponseRecord();
        outApiResponseRecord.setApiType(OutApiHttpUrlEnum.TANSHU_PHONE_OWNERSHIP_DATA.name);
        outApiResponseRecord.setCallType1(phoneNo);
        outApiResponseRecord.setResponse(body);
        outApiResponseRecordService.save(outApiResponseRecord);
        try {
            Map bodyMap = JSONUtil.toBean(body, Map.class);
            Object data = bodyMap.get("data");
            Map dataMap = JSONUtil.toBean(String.valueOf(data), Map.class);
            return new ResultDto<>(dataMap);
        } catch (Exception e) {
            log.error("解析返回报文失败。{}", body, e);
            return new ResultDto<>(400, "解析返回报文失败。");
        }
    }

    /**
     * 查询星座运势
     *
     * @return
     */
    @GetMapping("/queryconstellationfortune")
    public ResultDto<Map> queryConstellationFortune(@RequestParam("constellationId") String constellationId, @RequestParam("constellationFortuneType") String constellationFortuneType) {
        JSONConfig jsonConfig = new JSONConfig();
        //获取redis
        Object o = redisUtil.get(RedisConstants.OUT_API_PREFIX + OutApiHttpUrlEnum.TANSHU_CONSTELLATION_FORTUNE_DATA.name + ":" + constellationId + ":" + constellationFortuneType);
        if (Objects.nonNull(o)) {
            Map bodyMap = JSONUtil.toBean(String.valueOf(o), jsonConfig, Map.class);
            Object data = bodyMap.get("data");
            Map dataMap = JSONUtil.toBean(String.valueOf(data), Map.class);
            return new ResultDto<>(dataMap);
        }
        HttpRequest httpRequest = HttpRequest.get(String.format(OutApiHttpUrlEnum.TANSHU_CONSTELLATION_FORTUNE_DATA.url, tanshuApiKey, constellationId, constellationFortuneType));

        String body;
        try (HttpResponse execute = httpRequest.execute()) {
            body = execute.body();
        } catch (Exception e) {
            log.error("调{}失败。", OutApiHttpUrlEnum.TANSHU_CONSTELLATION_FORTUNE_DATA.name, e);
            return new ResultDto<>(400, "调外部api失败。");
        }
        redisUtil.set(RedisConstants.OUT_API_PREFIX + OutApiHttpUrlEnum.TANSHU_CONSTELLATION_FORTUNE_DATA.name + ":" + constellationId + ":" + constellationFortuneType, body, 24 * 60 * 60);
        //入表
        OutApiResponseRecord outApiResponseRecord = new OutApiResponseRecord();
        outApiResponseRecord.setApiType(OutApiHttpUrlEnum.TANSHU_CONSTELLATION_FORTUNE_DATA.name);
        outApiResponseRecord.setCallType1(constellationId);
        outApiResponseRecord.setCallType2(constellationFortuneType);
        outApiResponseRecord.setResponse(body);
        outApiResponseRecordService.save(outApiResponseRecord);
        try {
            Map bodyMap = JSONUtil.toBean(body, jsonConfig, Map.class);
            Object data = bodyMap.get("data");
            Map dataMap = JSONUtil.toBean(String.valueOf(data), Map.class);
            return new ResultDto<>(dataMap);
        } catch (Exception e) {
            log.error("解析返回报文失败。{}", body, e);
            return new ResultDto<>(400, "解析返回报文失败。");
        }
    }
}
