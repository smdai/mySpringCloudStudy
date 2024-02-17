package com.bztc.web.rest;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bztc.constant.Constants;
import com.bztc.domain.UserInfo;
import com.bztc.domain.UserRole;
import com.bztc.dto.Jscode2sessionDto;
import com.bztc.dto.ResultDto;
import com.bztc.dto.SessionInfoDto;
import com.bztc.enumeration.LoginEnum;
import com.bztc.service.SessionService;
import com.bztc.service.UserInfoService;
import com.bztc.service.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author daism
 * @create 2022-10-14 16:18
 * @description 微信小程序登录相关
 */
@RestController
@RequestMapping("/api/wxloginresource")
public class WxLoginResource {
    private static final Logger logger = LoggerFactory.getLogger(WxLoginResource.class);
    @Autowired
    public UserInfoService userInfoService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private SessionService sessionService;
    @Value("${wx.applet.appId}")
    private String appId;
    @Value("${wx.applet.appSecret}")
    private String appSecret;

    /**
     * 获取Jscode2sessionDto
     *
     * @return
     */
    @GetMapping("/getjscode2session")
    public ResultDto<SessionInfoDto> getJscode2session(@RequestParam("code") String code) {
        SessionInfoDto sessionInfoDto = new SessionInfoDto();
        HttpRequest get = HttpUtil.createGet(
                "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code");
        try (HttpResponse response = get.execute()) {
            String body = response.body();
            Jscode2sessionDto jscode2sessionDto = JSONUtil.toBean(body, Jscode2sessionDto.class);
            //判断是否新用户
            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("openid", jscode2sessionDto.getOpenid());
            UserInfo userInfo = userInfoService.getOne(queryWrapper);
            if (Objects.nonNull(userInfo)) {
                //老用户
                logger.info("老用户：{}", body);
            } else {
                logger.info("新用户：{}", body);
                //新用户，自动注册
                userInfo = new UserInfo();
                userInfo.setUserName(jscode2sessionDto.getOpenid());
                userInfo.setPassword(SecureUtil.md5(jscode2sessionDto.getOpenid()));
                userInfo.setStatus(Constants.STATUS_EFFECT);
                userInfo.setInputUser(Constants.ADMIN);
                userInfo.setUpdateUser(Constants.ADMIN);
                userInfo.setOpenid(jscode2sessionDto.getOpenid());
                try {
                    userInfoService.save(userInfo);
                    //赋予普通用户权限
                    UserRole userRole = new UserRole();
                    userRole.setUserId(userInfo.getId());
                    userRole.setRoleId(Constants.COMMON_ROLE_ID);
                    userRole.setStatus(Constants.STATUS_EFFECT);
                    userRole.setInputUser(Constants.ADMIN);
                    userRole.setUpdateUser(Constants.ADMIN);
                    userRoleService.save(userRole);
                } catch (DuplicateKeyException e) {
                    logger.error("用户名：【{}】已被注册，请更换其他用户名。", userInfo.getUserName(), e);
                    return new ResultDto<>(400, "用户名：【" + userInfo.getUserName() + "】已被注册，请更换其他用户名。");
                } catch (Exception e) {
                    logger.error("注册异常。", e);
                    return new ResultDto<>(400, "注册异常，请重试。");
                }
            }
            String token = sessionService.getToken(String.valueOf(userInfo.getId()));
            sessionService.setToken(userInfo.getId(), token);
            sessionInfoDto.setToken(token);
            sessionInfoDto.setUserId(userInfo.getId());
            sessionInfoDto.setLoginStatus(LoginEnum.SUCCESS.key);
            sessionInfoDto.setUserName(userInfo.getUserName());
            sessionInfoDto.setAvatarUrl(userInfo.getAvatarUrl());
            return new ResultDto<>(sessionInfoDto);
        } catch (Exception e) {
            logger.error("请求https://api.weixin.qq.com失败。", e);
            return new ResultDto<>();
        }
    }

}
