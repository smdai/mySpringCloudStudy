package com.bztc.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author daism
 * @create 2023-03-27 15:34
 * @description kafka配置
 */
@Component("KafkaProperties")
@ConfigurationProperties(prefix = "kafka.config")
@Data
@Slf4j
public class KafkaProperties {
    private String bootstrapServers;
    private String acks;
    private int retries;
    private int batchSize;
    private long lingerMs;
    private long bufferMemory;
    private String keySerializerClass;
    private String valueSerializerClass;
    /**
     * Producer 属性配置
     *
     * @return Properties
     */
    public Properties initProperties() {
        Properties properties = new Properties();
        // 指定连接IP和端口号
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        // 当producer向leader发送数据时，可以通request.required.acks参数来设置数据可靠性的级别，分别是0，1，all。
        properties.put(ProducerConfig.ACKS_CONFIG, acks);

        // 请求失败，生产者会自动重试，指定是0次，如果启用重试，则会有重复消息的可能性
        properties.put(ProducerConfig.RETRIES_CONFIG, retries);

        // 每个分区未发送消息总字节大小，单位：字节，超过设置的值就会提交数据到服务器，默认是16kb
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);

        // 消息在缓冲区保留的时间，超过设置的值就会提交到服务端
        properties.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);

        // 用来约束kafka producer能够使用的内存缓冲的大小，默认值32MB
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);

        // Key的序列化器，将用户提供的key和value对象ProducerRecord进行序列化处理，key.serializer必须被设置
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializerClass);

        // value的序列化器
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializerClass);

        return properties;
    }
}
