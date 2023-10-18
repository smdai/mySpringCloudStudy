package com.bztc.utils;

import com.bztc.config.KafkaProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author daism
 * @create 2023-03-27 09:33
 * @description kafka工具类
 */
@Slf4j
public class KafkaProducerUtil {
    /**
     * 发送kafka消息
     *
     * @param topic   主题
     * @param message 信息
     */
    public static void sendMessage(String topic, String message) {
        KafkaProperties kafkaProperties = ApplicationContextUtil.getBean("KafkaProperties", KafkaProperties.class);
        Properties properties = kafkaProperties.initProperties();
        try (Producer<String, String> producer = new KafkaProducer<>(properties)) {
            ProducerRecord<String, String> stringStringProducerRecord = new ProducerRecord<>(topic, message);
            producer.send(stringStringProducerRecord, (recordMetadata, e) -> {
                if (e != null) {
                    log.error("kafka发送信息失败！topic = {}", topic, e);
                } else {
                    log.info("kafka发送信息成功！topic = {},partition = {},offset = {}", topic, recordMetadata.partition(), recordMetadata.offset());
                }
            });
        } catch (Exception e) {
            log.error("kafka发送信息异常！topic = {}", topic, e);
        }
    }


}
