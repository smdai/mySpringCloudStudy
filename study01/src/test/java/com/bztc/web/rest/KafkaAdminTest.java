package com.bztc.web.rest;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * kafka admin 测试
 */
@Slf4j
public class KafkaAdminTest {

    /**
     * 创建AdminClient
     *
     * @return AdminClient
     */
    public static AdminClient initAdminClient() {
        Properties properties = new Properties();
        // 指定连接IP和端口号
        properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "10.8.0.1:9092");
        return AdminClient.create(properties);
    }

    /**
     * 创建Topic
     * 注意防火墙：https://www.cnblogs.com/hellocjr/p/11431230.html
     * ./bin/kafka-topics.sh --create --zookeeper 192.168.3.99:2181 --replication-factor 1 --partitions 1 --topic test-topic
     */
    @Test
    @DisplayName("创建Topic")
    public void createTopic_test() {
        AdminClient adminClient = initAdminClient();
        // 指定分区数量、副本数量
        NewTopic newTopic = new NewTopic("test-topic", 2, (short) 1);
        CreateTopicsResult createTopicsResult = adminClient.createTopics(Collections.singletonList(newTopic));
        try {
            // KafkaFuture等待创建，成功则不会有任何报错
            createTopicsResult.all().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 列举Topic列表
     * ./bin/kafka-topics.sh --list --zookeeper 192.168.3.99:2181
     */
    @Test
    @DisplayName("列举Topic列表")
    public void listTopic_test() {
        AdminClient adminClient = initAdminClient();
        ListTopicsOptions listTopicsOptions = new ListTopicsOptions();
        // 是否查看内部Topic
        listTopicsOptions.listInternal(false);
        ListTopicsResult listTopicsResult = adminClient.listTopics(listTopicsOptions);
        Set<String> set = Collections.emptySet();
        try {
            set = listTopicsResult.names().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        set.forEach(System.out::println);
    }

    /**
     * 删除Topic
     * ./bin/kafka-topics.sh --zookeeper 192.168.3.99:2181 --delete --topic test-topic
     */
    @Test
    @DisplayName("删除Topic")
    public void deleteTopic_test() {
        AdminClient adminClient = initAdminClient();
        DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(Collections.singletonList("test-topic"));
        try {
            deleteTopicsResult.all().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看某个Topic详情
     * ./bin/kafka-topics.sh --describe --zookeeper 192.168.3.99:2181 --topic test-topic
     */
    @Test
    @DisplayName("查看某个Topic详情")
    public void describeTopics_test() {
        AdminClient adminClient = initAdminClient();
        DescribeTopicsResult describeTopicsResult = adminClient.describeTopics(Collections.singletonList("test-topic"));
        Map<String, TopicDescription> stringTopicDescriptionMap = Collections.emptyMap();
        try {
            stringTopicDescriptionMap = describeTopicsResult.all().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        stringTopicDescriptionMap.forEach((topicName, topicDescription) -> {
            System.out.println("name：" + topicName + "，desc：" + topicDescription);
        });
    }

    /**
     * 增加分区数量
     * ./bin/kafka-topics.sh --alter --zookeeper 192.168.3.99:2181 --partitions 5 --topic test-topic
     * WARNING: If partitions are increased for a topic that has a key, the partition logic or ordering of the messages will be affected
     * 如果当主题中的消息包含有key时（即key不为null）根据key来计算分区的行为就会有所影响消息的顺序性
     * 注意：kafka中的分区数量智能增加不能减少
     */
    @Test
    @DisplayName("增加分区数量")
    public void createPartitions_test() {
        Map<String, NewPartitions> map = new HashMap<>();
        NewPartitions newPartitions = NewPartitions.increaseTo(3);
        map.put("test-topic", newPartitions);
        AdminClient adminClient = initAdminClient();
        CreatePartitionsResult createPartitionsResult = adminClient.createPartitions(map);
        try {
            createPartitionsResult.all().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}


