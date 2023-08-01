package com.bztc.redis;

import com.bztc.Application8001Run;
import com.bztc.service.CodeLibraryService;
import com.bztc.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author daism
 * @create 2023-04-10 09:50
 * @description redis测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application8001Run.class)
@Slf4j
public class RedisUtilTest {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    CodeLibraryService codeLibraryService;

    public static void main(String[] args) {
        String s = "c";
        switch (s) {
            case "s":
                System.out.println("abc");
                break;
            case "a":
                System.out.println("a");
                break;
            case "b":
            case "c":
            case "d":
                System.out.println("bcd");
                break;
            default:
                break;
        }
    }

    @Test
    public void setTest() {
        redisUtil.set("daism", "aaaaaaaaaaaaa");
    }
}
