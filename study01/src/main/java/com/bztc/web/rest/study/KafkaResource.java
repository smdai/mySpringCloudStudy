package com.bztc.web.rest.study;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bztc.constant.EsIndexConstants;
import com.bztc.constant.KafkaConstants;
import com.bztc.domain.study.UserInfoStudy;
import com.bztc.dto.ResultDto;
import com.bztc.enumeration.LoginEnum;
import com.bztc.enumeration.WebStatusEnum;
import com.bztc.service.KafkaService;
import com.bztc.utils.EsIndexUtil;
import com.bztc.utils.KafkaProducerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author daism
 * @create 2023-03-29 16:22
 * @description kafka学习
 */
@RestController
@RequestMapping("/bztcSystem/api/kafkaResource")
@Slf4j
public class KafkaResource {
    @Autowired
    KafkaService kafkaService;

    /**
     * @param params
     * @return
     */
    @GetMapping("/queryByPage")
    public ResultDto<List<UserInfoStudy>> queryByPage(@RequestParam("param") String params) {
        log.info("分页查询入参：{}", params);
        JSONObject jsonObject = JSONUtil.parseObj(params);
        return kafkaService.queryByPage(EsIndexUtil.getAllIndex(EsIndexConstants.BZTC_KAFKA_USERINFO_JSON), jsonObject);
    }
    /**
     * 删除es
     *
     * @param userInfo
     * @return
     */
    @PostMapping("/deleteEs")
    public ResultDto<Integer> deleteEs(@RequestBody UserInfoStudy userInfo) {
        log.info("删除es入参：{}", JSONUtil.toJsonStr(userInfo));
        kafkaService.deleteEs(EsIndexUtil.getYearMonthIndexByInputDate(EsIndexConstants.BZTC_KAFKA_USERINFO_JSON,userInfo.getInputDate()), userInfo);
        ResultDto<Integer> resultDto = new ResultDto<>();
        resultDto.setCode(200);
        resultDto.setMessage(LoginEnum.lookup(200));
        return resultDto;
    }
    /**
     * 插入es
     *
     * @param userInfo
     * @return
     */
    @PostMapping("/insertEs")
    public ResultDto<Integer> insertEs(@RequestBody UserInfoStudy userInfo) {
        log.info("插入es入参：{}", JSONUtil.toJsonStr(userInfo));
        userInfo.setInputDate(LocalDate.now());
        userInfo.setUpdateDate(LocalDate.now());
        userInfo.setInputTime(LocalDateTime.now());
        userInfo.setUpdateTime(LocalDateTime.now());
        kafkaService.insertEs(EsIndexUtil.getYearMonthIndex(EsIndexConstants.BZTC_KAFKA_USERINFO_JSON), userInfo);
        ResultDto<Integer> resultDto = new ResultDto<>();
        resultDto.setCode(200);
        resultDto.setMessage(LoginEnum.lookup(200));
        return resultDto;
    }
    /**
     * 修改es
     *
     * @param userInfo
     * @return
     */
    @PostMapping("/updateEs")
    public ResultDto<Integer> updateEs(@RequestBody UserInfoStudy userInfo) {
        log.info("修改es入参：{}", JSONUtil.toJsonStr(userInfo));
        userInfo.setUpdateDate(LocalDate.now());
        userInfo.setUpdateTime(LocalDateTime.now());
        kafkaService.updateEs(EsIndexUtil.getYearMonthIndexByInputDate(EsIndexConstants.BZTC_KAFKA_USERINFO_JSON,userInfo.getInputDate()), userInfo);
        ResultDto<Integer> resultDto = new ResultDto<>();
        resultDto.setCode(200);
        resultDto.setMessage(LoginEnum.lookup(200));
        return resultDto;
    }

    /**
     * 通过kafka插入es
     *
     * @param userInfo
     * @return
     */
    @PostMapping("/insertEsByKafka")
    public ResultDto<Integer> insertEsByKafka(@RequestBody UserInfoStudy userInfo) {
        log.info("通过kafka插入es入参：{}", JSONUtil.toJsonStr(userInfo));
        ResultDto<Integer> resultDto = new ResultDto<>();
        try {
            KafkaProducerUtil.sendMessage(KafkaConstants.BZTC_KAFKA_USERINFO_JSON_TOPIC, JSONUtil.toJsonStr(userInfo));
            resultDto.setCode(WebStatusEnum.SUCCESS.key);
            resultDto.setMessage(WebStatusEnum.SUCCESS.value);
        } catch (Exception e) {
            log.error("通过kafka插入es异常", e);
            resultDto.setCode(WebStatusEnum.WRONG_ERROR.key);
            resultDto.setMessage(WebStatusEnum.WRONG_ERROR.value);
        }
        return resultDto;
    }
}
