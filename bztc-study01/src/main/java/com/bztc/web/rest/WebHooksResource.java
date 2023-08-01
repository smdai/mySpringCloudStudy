package com.bztc.web.rest;

import com.bztc.dto.AlarmMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author daism
 * @create 2022-09-25 20:43
 * @description webhooks钩子实现
 */
@RestController
@RequestMapping("/webHooksResource")
public class WebHooksResource {
    private static final Logger logger = LoggerFactory.getLogger(WebHooksResource.class);

    /**
     * 描述：告警通知
     *
     * @author daism
     * @date 2022-09-25 20:45:22
     */
    @PostMapping("/notifyTest")
    public void notifyTest(@RequestBody List<AlarmMessageDto> alarmMessageList) {
        logger.info("notifyTest----->告警通知:{}", alarmMessageList);
    }
}
