package com.bztc.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.constant.RabbitMqConstants;
import com.bztc.domain.BztcRabbitmqMessageRecord;
import com.bztc.mapper.BztcRabbitmqMessageRecordMapper;
import com.bztc.service.BztcRabbitmqMessageRecordService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author daishuming
 * @description 针对表【bztc_rabbitmq_message_record(rabbitmq记录表)】的数据库操作Service实现
 * @createDate 2023-09-04 18:50:42
 */
@Service
public class BztcRabbitmqMessageRecordServiceImpl extends ServiceImpl<BztcRabbitmqMessageRecordMapper, BztcRabbitmqMessageRecord>
        implements BztcRabbitmqMessageRecordService {
    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送mq消息
     *
     * @param bztcRabbitmqMessageRecord mq记录
     * @return 成功标识
     */
    @Override
    public boolean sendMqMessage(BztcRabbitmqMessageRecord bztcRabbitmqMessageRecord) {
        amqpTemplate.convertAndSend(RabbitMqConstants.QUEUE_BZTC_RABBITMQ_MESSAGE_CREATED, JSONUtil.toJsonStr(bztcRabbitmqMessageRecord));
        return true;
    }
}




