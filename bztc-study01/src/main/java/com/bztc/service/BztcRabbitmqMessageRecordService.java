package com.bztc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bztc.domain.BztcRabbitmqMessageRecord;

/**
 * @author daishuming
 * @description 针对表【bztc_rabbitmq_message_record(rabbitmq记录表)】的数据库操作Service
 * @createDate 2023-09-04 18:50:42
 */
public interface BztcRabbitmqMessageRecordService extends IService<BztcRabbitmqMessageRecord> {
    /**
     * 发送mq消息
     *
     * @param bztcRabbitmqMessageRecord mq记录
     * @return 成功标识
     */
    boolean sendMqMessage(BztcRabbitmqMessageRecord bztcRabbitmqMessageRecord);
}
