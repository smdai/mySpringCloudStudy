package com.bztc.web.rest;

import com.bztc.domain.BztcRabbitmqMessageRecord;
import com.bztc.dto.ResultDto;
import com.bztc.service.BztcRabbitmqMessageRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author daism
 * @create 2023-09-04 18:52
 * @description rabbitmq resource层
 */
@RestController
@RequestMapping("/api/rabbitmqresource")
public class RabbitMqResource {
    @Autowired
    private BztcRabbitmqMessageRecordService rabbitmqMessageRecordService;

    /**
     * 发送mq消息
     *
     * @param bztcRabbitmqMessageRecord mq消息
     * @return 成功or失败
     */
    @PostMapping("/sendRabbitMqMessage")
    public ResultDto<String> sendRabbitMqMessage(@RequestBody BztcRabbitmqMessageRecord bztcRabbitmqMessageRecord) {
        ResultDto<String> resultDto = new ResultDto<>();
        if (rabbitmqMessageRecordService.sendMqMessage(bztcRabbitmqMessageRecord)) {
            resultDto.setCode(200);
            resultDto.setMessage("成功。");
            return resultDto;
        }
        resultDto.setCode(400);
        resultDto.setMessage("失败。");
        return resultDto;
    }
}
