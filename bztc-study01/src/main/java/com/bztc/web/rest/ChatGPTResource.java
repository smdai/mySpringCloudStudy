package com.bztc.web.rest;

import cn.hutool.json.JSONUtil;
import com.bztc.dto.ChatGptDto;
import com.bztc.dto.ResultDto;
import io.github.asleepyfish.util.OpenAiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author daism
 * @create 2023-05-09 18:30
 * @description chatgpt
 */
@RestController
@RequestMapping("/api/chatgptResource")
@Slf4j
public class ChatGPTResource {
    /**
     * 描述：查询
     *
     * @return com.bztc.dto.ResultDto<com.bztc.entity.WebsiteList>
     * @author daism
     * @date 2022-09-29 10:35:10
     */
    @PostMapping("/queryChat")
    public ResultDto<String> insert(@RequestBody ChatGptDto chatGptDto) {
        log.info("查询chatgpt入参：{}", JSONUtil.toJsonStr(chatGptDto.getMessage()));
        List<String> list = OpenAiUtils.createChatCompletion(chatGptDto.getMessage());
        String retMsg = String.join("", list);
        log.info("查询chatgpt出参：{}", retMsg);
        return new ResultDto<>(retMsg);
//        return new ResultDto<String>("啊啊啊啊啊啊啊啊啊啊啊");
    }
}
