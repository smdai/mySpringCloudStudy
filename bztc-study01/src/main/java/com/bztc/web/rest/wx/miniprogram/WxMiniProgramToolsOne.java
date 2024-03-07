package com.bztc.web.rest.wx.miniprogram;

import com.bztc.dto.ResultDto;
import com.bztc.utils.YamlPropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author daism
 * @create 2024-03-07 15:25
 * @description 微信小程序-工具相关1
 */
@RestController
@RequestMapping("/api/wx/miniprogram/wxminiprogramtoolsone")
@Slf4j
public class WxMiniProgramToolsOne {
    @PostMapping("/convertyamltoproperties")
    public ResultDto<String> convertYamlToProperties(@RequestBody String yamlText) {
        return new ResultDto<>(YamlPropertiesUtil.castToProperties(yamlText));
    }

    @PostMapping("/convertpropertiestoyaml")
    public ResultDto<String> convertPropertiesToYaml(@RequestBody String propertiesText) {
        return new ResultDto<>(YamlPropertiesUtil.castToYaml(propertiesText));
    }
}
