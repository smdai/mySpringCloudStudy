package com.bztc.web.rest;

import cn.hutool.json.JSONUtil;
import com.bztc.Application8001Run;
import com.bztc.dto.ResultDto;
import com.bztc.dto.SessionInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author daism
 * @create 2023-04-13 11:54
 * @description session相关测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application8001Run.class)
@Slf4j
public class SessionResourceTest {
    @Autowired
    SessionResource sessionResource;

    @Test
    public void getSessionTest() {
        ResultDto<SessionInfoDto> admin = sessionResource.getSession("admin");
        System.out.println(JSONUtil.toJsonStr(admin));
    }
}
