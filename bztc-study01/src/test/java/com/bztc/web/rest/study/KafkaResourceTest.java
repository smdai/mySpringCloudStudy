package com.bztc.web.rest.study;

import com.bztc.Application8001Run;
import com.bztc.domain.study.UserInfoStudy;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author daism
 * @create 2023-03-29 16:22
 * @description kafka学习
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application8001Run.class)
@Slf4j
public class KafkaResourceTest {
    @Autowired
    KafkaResource kafkaResource;
    /**
     * 插入es
     * @return
     */
    @Test
    public void insertEsTest(){
    }
    /**
     * 通过kafka插入es
     * @return
     */
    @Test
    public void insertEsByKafkaTest(){
        UserInfoStudy userInfoStudy = new UserInfoStudy();
        userInfoStudy.setId("100L");
        userInfoStudy.setName("daism");
        userInfoStudy.setAge(22);
        userInfoStudy.setAddress("江苏省南京市玄武区");
        userInfoStudy.setPhone("12222222233");
        userInfoStudy.setSex("男");
        userInfoStudy.setInputDate(LocalDate.now());
        userInfoStudy.setUpdateDate(LocalDate.now());
        userInfoStudy.setInputTime(LocalDateTime.now());
        userInfoStudy.setUpdateTime(LocalDateTime.now());
        kafkaResource.insertEsByKafka(userInfoStudy);
    }
}
