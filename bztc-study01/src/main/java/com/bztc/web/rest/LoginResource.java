package com.bztc.web.rest;

import cn.hutool.json.JSONUtil;
import com.bztc.domain.UserInfo;
import com.bztc.dto.ResultDto;
import com.bztc.dto.SessionInfoDto;
import com.bztc.enumeration.LoginEnum;
import com.bztc.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author daism
 * @create 2022-10-14 16:18
 * @description 登录相关
 */
@RestController
@RequestMapping("/api/loginResource")
public class LoginResource {
    private static final Logger logger = LoggerFactory.getLogger(LoginResource.class);
    @Autowired
    public UserInfoService userInfoService;

    /**
     * 描述：用户登录
     *
     * @return com.bztc.dto.ResultDto<java.lang.Integer>
     * @author daism
     * @date 2022-10-14 17:46:00
     */
    @PostMapping("/login")
    public ResultDto<SessionInfoDto> insert(@RequestBody UserInfo userInfo) {
        logger.info("登录入参：{}", JSONUtil.toJsonStr(userInfo));
        ResultDto<SessionInfoDto> resultDto = new ResultDto<>();
        SessionInfoDto sessionInfoDto = userInfoService.login(userInfo);
        resultDto.setCode(sessionInfoDto.getLoginStatus());
        resultDto.setMessage(LoginEnum.lookup(sessionInfoDto.getLoginStatus()));
        if (sessionInfoDto.getLoginStatus() == LoginEnum.SUCCESS.key) {
            resultDto.setData(sessionInfoDto);
        }
        return resultDto;
    }
}
