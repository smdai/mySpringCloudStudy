package com.bztc.web.rest;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class KafkaProduceTest {

    /**
     * Producer 属性配置
     *
     * @return Properties
     */
    public static Properties initProperties() {
        Properties properties = new Properties();

        // 指定连接IP和端口号
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.8.0.1:9092");

        // 当producer向leader发送数据时，可以通request.required.acks参数来设置数据可靠性的级别，分别是0，1，all。
        properties.put(ProducerConfig.ACKS_CONFIG, "all");

        // 请求失败，生产者会自动重试，指定是0次，如果启用重试，则会有重复消息的可能性
        properties.put(ProducerConfig.RETRIES_CONFIG, 0);

        // 每个分区未发送消息总字节大小，单位：字节，超过设置的值就会提交数据到服务器，默认是16kb
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);

        // 消息在缓冲区保留的时间，超过设置的值就会提交到服务端
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);

        // 用来约束kafka producer能够使用的内存缓冲的大小，默认值32MB
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);

        // Key的序列化器，将用户提供的key和value对象ProducerRecord进行序列化处理，key.serializer必须被设置
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        // value的序列化器
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        return properties;
    }

    /**
     * send()方法是异步的，添加消食到缓冲区等待发送，并立即返回
     * 生产者将单个的消息批量在一起发送来提高效率，即batch.size和linger.ms结合
     * <p>
     * 实现同步发送：一条消息发送之后，会阻塞当前线程，直至返回ack
     * 发送消息后返回一个Future对象，调用get()即可
     * <p>
     * 消息发送主要是两个线程：一个是Main用户主线程，一个是Sender的线程
     * 1.main线程主要发送消息到RecordAccumulator即返回
     * 2.sender线程从RecordAccumulator拉去信息发送到broker
     * 3.batch.size和linger.ms两个参数可以影响sender线程发送次数
     */
    @Test
    @DisplayName("生产者发送消息")
    public void producer_send_test() {
        Properties properties = initProperties();
        Producer<String, String> producer = new KafkaProducer<String, String>(properties);
        ProducerRecord<String, String> stringStringProducerRecord = new ProducerRecord<>("test-topic", "key1", "value1");
        Future<RecordMetadata> future = producer.send(stringStringProducerRecord);
        try {
            // 同步阻塞
            // 不关心发送结果不需写这行
            RecordMetadata recordMetadata = future.get();
            // RecordMetadata格式：topic + "-" + partition + "@" + offset;
            System.out.println("发送状态：" + recordMetadata.toString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        // 关闭
        producer.close();
    }

}
