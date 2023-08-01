package com.bztc.test;

import com.bztc.Application8001Run;
import io.github.asleepyfish.util.OpenAiUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author daism
 * @create 2023-05-09 17:21
 * @description chatgpt test
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application8001Run.class)
@Slf4j
public class ChatTest {
    @Test
    public void chatGPTTest() {
        OpenAiUtils.createChatCompletion("用java写个demo").forEach(System.out::println);
    }
}
