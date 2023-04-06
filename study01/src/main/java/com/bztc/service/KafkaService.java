package com.bztc.service;

import cn.hutool.json.JSONObject;
import com.bztc.domain.study.UserInfoStudy;
import com.bztc.dto.ResultDto;

import java.util.List;

public interface KafkaService {
    void insertEs(String indexName,UserInfoStudy userInfo);
    void updateEs(String indexName,UserInfoStudy userInfo);
    void deleteEs(String indexName,UserInfoStudy userInfo);
    ResultDto<List<UserInfoStudy>> queryByPage(String indexName, JSONObject jsonObject);
}
