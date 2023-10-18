package com.bztc.web.rest;

import com.bztc.Application8001Run;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author daism
 * @create 2023-03-24 15:36
 * @description Kakfa测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application8001Run.class)
@Slf4j
public class KafkaTest {
    @Test
    public void kafkaConsumerTest() {
        Properties prop = new Properties();

        prop.put("bootstrap.servers", "10.8.0.1:9092");
        prop.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");

        prop.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        prop.put("group.id", "con-1");
        prop.put("auto.offset.reset", "latest");
        //自动提交偏移量
        prop.put("auto.commit.intervals.ms", "true");
        //自动提交时间
        prop.put("auto.commit.interval.ms", "1000");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(prop);
        ArrayList<String> topics = new ArrayList<>();
        //可以订阅多个消息
        topics.add("bztc-test1-kafka-topic");
        consumer.subscribe(topics);
        while (true) {
            ConsumerRecords<String, String> poll = consumer.poll(Duration.ofSeconds(20));
            for (ConsumerRecord<String, String> consumerRecord : poll) {
                System.out.println(consumerRecord);
            }
        }

    }

    @Test
    public void kafkaProducerTest() {
        Properties prop = new Properties();

        prop.put("bootstrap.servers", "10.8.0.1:9092");
        prop.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        prop.put("acks", "all");
        prop.put("retries", 0);
        prop.put("batch.size", 16384);
        prop.put("linger.ms", 1);
        prop.put("buffer.memory", 33554432);
        String topic = "kafka-test1";
        KafkaProducer<String, String> producer = new KafkaProducer<>(prop);
        producer.send(new ProducerRecord<>(topic, Integer.toString(2), "hello kafka4"));
        producer.close();
    }

}
