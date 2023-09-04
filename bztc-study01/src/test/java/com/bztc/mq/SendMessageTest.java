package com.bztc.mq;

import com.bztc.Application8001Run;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author daism
 * @create 2023-09-04 11:02
 * @description mq测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application8001Run.class)
@Slf4j
public class SendMessageTest {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void sendMessageTest() {
        amqpTemplate.convertAndSend("myTest", "test one message");
    }

    @Test
    public void sendMessageTest1() {
        amqpTemplate.convertAndSend("myQueue", "监听到了已创建好的queue:myQueue。");
    }

    /**
     * @program: sell_order
     * @description: 发送消息，即消息发布者
     * @author: 01
     * @create: 2018-08-21 22:28
     **/
    @Test
    public void sendOrder() {
        for (int i = 0; i < 100; i++) {
            // 第一个参数指定队列，第二个参数来指定路由的key，第三个参数指定消息
            amqpTemplate.convertAndSend("testOrder", "computer", "第" + i + "条消息");
        }
    }
}
