package com.bztc.service;

import com.bztc.domain.study.UserInfoStudy;
import com.bztc.dto.ResultDto;
import com.bztc.dto.study.UserInfoStudyDto;

import java.util.List;

public interface KafkaService {
    void insertEs(String indexName,UserInfoStudy userInfo);
    void updateEs(String indexName,UserInfoStudy userInfo);
    void deleteEs(String indexName,UserInfoStudy userInfo);
    ResultDto<List<UserInfoStudy>> queryByPage(String indexName, UserInfoStudyDto userInfoStudyDto);
}
