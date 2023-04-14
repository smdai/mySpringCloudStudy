package com.bztc.web.rest.study;

import cn.hutool.json.JSONUtil;
import com.bztc.Application8001Run;
import com.bztc.constant.EsIndexConstants;
import com.bztc.domain.study.UserInfoStudy;
import com.bztc.utils.EsIndexUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author daism
 * @create 2023-04-06 11:43
 * @description es测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application8001Run.class)
@Slf4j
public class EsTest {
    @Autowired
    RestHighLevelClient client;

    /**
     * 测试插入数据
     *
     * @throws IOException
     */
    @Test
    public void insertDoc() throws IOException {
        for(int i= 0 ; i < 100 ; i ++){
            UserInfoStudy userInfoStudy = new UserInfoStudy();
            userInfoStudy.setId(i+"");
            userInfoStudy.setName("是我"+i);
            userInfoStudy.setAge(18+i);
            userInfoStudy.setPhone("111111");
            userInfoStudy.setSex("男");
            userInfoStudy.setAddress("江苏省北京市"+i);
            userInfoStudy.setInputDate(LocalDate.now());
            userInfoStudy.setUpdateDate(LocalDate.now());
            userInfoStudy.setInputTime(LocalDateTime.now());
            userInfoStudy.setUpdateTime(LocalDateTime.now());
            IndexRequest request = new IndexRequest(EsIndexConstants.BZTC_KAFKA_USERINFO_JSON+"2023.01");
            request.source(JSONUtil.toJsonStr(userInfoStudy), XContentType.JSON);
            IndexResponse index = client.index(request, RequestOptions.DEFAULT);
            System.out.println(index);
        }

    }

    /**
     * 创建索引测试
     *
     * @throws IOException
     */
    @Test
    public void esClientTest() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(EsIndexUtil.getYearMonthIndex(EsIndexConstants.BZTC_KAFKA_USERINFO_JSON));
        client.indices().create(request, RequestOptions.DEFAULT);
    }
}
