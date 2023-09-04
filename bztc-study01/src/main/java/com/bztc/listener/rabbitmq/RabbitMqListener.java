package com.bztc.listener.rabbitmq;

import cn.hutool.json.JSONUtil;
import com.bztc.constant.RabbitMqConstants;
import com.bztc.domain.BztcRabbitmqMessageRecord;
import com.bztc.service.BztcRabbitmqMessageRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author daism
 * @create 2023-09-04 18:28
 * @description rabbitmq监听
 */
@Slf4j
@Component
public class RabbitMqListener {
    @Autowired
    BztcRabbitmqMessageRecordService bztcRabbitmqMessageRecordService;

    /**
     * 接收并打印消息
     * 监听已经创建好的queue
     *
     * @param message 消息
     */
    @RabbitListener(queues = RabbitMqConstants.QUEUE_BZTC_RABBITMQ_MESSAGE_CREATED)
    public void listenCreatedQueue(String message) {
        log.info("listenCreatedQueue (监听已经创建好的queue) queue rabbitmq 监听到消息：{}", message);
        BztcRabbitmqMessageRecord bztcRabbitmqMessageRecord = JSONUtil.toBean(message, BztcRabbitmqMessageRecord.class);
        boolean save = bztcRabbitmqMessageRecordService.save(bztcRabbitmqMessageRecord);
        log.info("listenCreatedQueue (监听已经创建好的queue) queue rabbitmq 结束。save-flag:{}", save);
    }

    /**
     * 接收并打印消息
     * 可以当队列不存在时自动创建队列
     *
     * @param message message
     */
    @RabbitListener(queuesToDeclare = @Queue(RabbitMqConstants.QUEUE_BZTC_RABBITMQ_MESSAGE_NOT_CREATED))
    public void listenNotCreatedQueue(String message) {
        log.info("listenNotCreatedQueue (可以当队列不存在时自动创建队列) queue rabbitmq 监听到消息：{}", message);
    }
}
