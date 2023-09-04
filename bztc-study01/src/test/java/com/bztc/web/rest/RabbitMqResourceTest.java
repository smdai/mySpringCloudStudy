package com.bztc.web.rest;

import cn.hutool.json.JSONUtil;
import com.bztc.Application8001Run;
import com.bztc.constant.Constants;
import com.bztc.domain.BztcRabbitmqMessageRecord;
import com.bztc.dto.ResultDto;
import com.bztc.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author daism
 * @create 2023-09-04 19:10
 * @description RabbitMqResource测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application8001Run.class)
@Slf4j
public class RabbitMqResourceTest {
    @Autowired
    RabbitMqResource rabbitMqResource;

    @Test
    public void sendRabbitMqMessageTest() {
        BztcRabbitmqMessageRecord bztcRabbitmqMessageRecord = new BztcRabbitmqMessageRecord();
        bztcRabbitmqMessageRecord.setMethodType(Constants.RABBIT_MQ_METHOD_TYPE);
        bztcRabbitmqMessageRecord.setMessage("这个我已创建好的queue。时间：" + DateUtil.getLocalTimeNowStr());
        ResultDto<String> resultDto = rabbitMqResource.sendRabbitMqMessage(bztcRabbitmqMessageRecord);
        log.info(JSONUtil.toJsonStr(resultDto));
    }
}
