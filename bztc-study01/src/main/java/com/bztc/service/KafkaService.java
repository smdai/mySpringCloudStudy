package com.bztc.service;

import com.bztc.domain.study.UserInfoStudy;
import com.bztc.dto.ResultDto;
import com.bztc.dto.study.UserInfoStudyDto;

import java.util.List;

public interface KafkaService {
    /**
     * 直接插入es
     *
     * @param indexName 索引名称
     * @param userInfo  用户信息
     */
    void insertEs(String indexName, UserInfoStudy userInfo);

    /**
     * 直接更新es
     *
     * @param indexName 索引名称
     * @param userInfo  用户信息
     */
    void updateEs(String indexName, UserInfoStudy userInfo);

    /**
     * 直接删除es
     *
     * @param indexName 索引名称
     * @param userInfo  用户信息
     */
    void deleteEs(String indexName, UserInfoStudy userInfo);

    /**
     * 分页查询
     *
     * @param indexName        索引名称
     * @param userInfoStudyDto 用户信息
     * @return 用户信息列表
     */
    ResultDto<List<UserInfoStudy>> queryByPage(String indexName, UserInfoStudyDto userInfoStudyDto);
}
