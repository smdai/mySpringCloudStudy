package com.bztc.web.rest;

import com.bztc.Application8001Run;
import com.bztc.dto.ResultDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author daism
 * @create 2024-02-26 19:31
 * @description test
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application8001Run.class)
@Slf4j
public class WxLoginResourceTest {
    @Autowired
    WxLoginResource wxLoginResource;

    @Test
    public void testGetAccessToken() {
        ResultDto<String> accessToken = wxLoginResource.getMiniProgramQrCode("aaaa");
        System.out.println(accessToken);
    }
}
