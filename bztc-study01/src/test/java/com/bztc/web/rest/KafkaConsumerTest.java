package com.bztc.web.rest;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class KafkaConsumerTest {

    public static Properties initProperties() {
        Properties properties = new Properties();

        // broker地址
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.8.0.1:9092");

        // 消费者分组ID，分组内的消费者只能消费该消息一次，不同分组内的消费者可以重复消费该消息
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group-3");

        // 开启自动提交offset
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        // 自动提交offset延迟时间
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "10000");

        // 默认是latest，如果需要从头开始消费partition消息，需改为earliest，且消费者组名变更才生效
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // 反序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        return properties;
    }

    @Test
    @DisplayName("消费者消费消息")
    public void consumer_pull_test() {
        Properties properties = initProperties();
        Consumer<String, String> consumer = new KafkaConsumer<>(properties);
        // 订阅主题
        consumer.subscribe(Collections.singletonList("test-topic"));
        while (true) {
            // 100ms阻塞超时时间
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(100));
            consumerRecords.forEach((ConsumerRecord<String, String> consumerRecord) -> {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~");
                log.info("Topic is {}，Offset is {}，Key is {}，Value is {}", consumerRecord.topic(), consumerRecord.offset(), consumerRecord.key(), consumerRecord.value());
                System.out.println("~~~~~~~~~~~~~~~~~~~~~");
            });

            // 非空才手工提交
            if (!consumerRecords.isEmpty()) {
                // commitSync同步阻塞当前线程（自动失败重试）
                // consumer.commitSync();
                // commitAsync异步不会阻塞当前线程，没有失败重试，回调callback函数获取提交信息，记录日志
                consumer.commitAsync((offsets, exception) -> {
                    if (Objects.isNull(exception)) {
                        System.out.println("手工提交Offset成功：" + offsets.toString());
                    } else {
                        System.out.println("手工提交Offset失败：" + offsets.toString());
                        exception.printStackTrace();
                    }
                });
            }
        }
    }
}

