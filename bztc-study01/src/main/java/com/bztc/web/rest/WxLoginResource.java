package com.bztc.web.rest;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
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
import com.bztc.dto.WxBindDto;
import com.bztc.enumeration.LoginEnum;
import com.bztc.service.SessionService;
import com.bztc.service.UserInfoService;
import com.bztc.service.UserRoleService;
import com.bztc.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @Value("${bztc.defult.avatar.path}")
    private String defultAvatarPath;

    /**
     * 获取Jscode2sessionDto
     *
     * @return
     */
    @GetMapping("/getjscode2session")
    @Transactional
    public ResultDto<SessionInfoDto> getJscode2session(@RequestParam("code") String code) {
        SessionInfoDto sessionInfoDto = new SessionInfoDto();
        String openid = "";
        HttpRequest get = HttpUtil.createGet(
                "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code");
        try (HttpResponse response = get.execute()) {
            String body = response.body();
            Jscode2sessionDto jscode2sessionDto = JSONUtil.toBean(body, Jscode2sessionDto.class);
            openid = jscode2sessionDto.getOpenid();
        } catch (Exception e) {
            logger.error("请求https://api.weixin.qq.com失败。", e);
            return new ResultDto<>();
        }
        //判断是否新用户
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        UserInfo userInfo = userInfoService.getOne(queryWrapper);
        if (Objects.nonNull(userInfo)) {
            //老用户
            logger.info("老用户：{}", openid);
        } else {
            logger.info("新用户：{}", openid);
            //新用户，自动注册
            userInfo = new UserInfo();
            userInfo.setUserName(openid);
            userInfo.setPassword(SecureUtil.md5(openid));
            userInfo.setStatus(Constants.STATUS_EFFECT);
            userInfo.setInputUser(Constants.ADMIN);
            userInfo.setUpdateUser(Constants.ADMIN);
            userInfo.setOpenid(openid);
            userInfo.setAvatarUrl(Constants.IMAGE_PREFIX + defultAvatarPath);
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
        sessionInfoDto.setUserName(userInfo.getUserName().equals(userInfo.getOpenid()) ? userInfo.getUserName().substring(0, 8) : userInfo.getUserName());
        sessionInfoDto.setAvatarUrl(userInfo.getAvatarUrl());
        return new ResultDto<>(sessionInfoDto);
    }

    /**
     * 微信绑定
     *
     * @param wxBindDto
     * @return
     */
    @PostMapping("/wxbind")
    @Transactional
    public ResultDto<String> wxBind(@RequestBody WxBindDto wxBindDto) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", wxBindDto.getUserName());
        UserInfo userNameUserInfo = userInfoService.getOne(queryWrapper);
        if (Objects.isNull(userNameUserInfo) || !userNameUserInfo.getPassword().equals(wxBindDto.getPassword())) {
            return new ResultDto<>(400, "用户名或密码不正确。");
        }
        if (StrUtil.isNotBlank(userNameUserInfo.getOpenid())) {
            return new ResultDto<>(400, "该账号已绑定了微信号，请勿重复绑定。");
        }
        //账号密码中的用户id
        int accountUserId = userNameUserInfo.getId();
        String openid = "";
        HttpRequest get = HttpUtil.createGet(
                "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + appSecret + "&js_code=" + wxBindDto.getCode() + "&grant_type=authorization_code");
        try (HttpResponse response = get.execute()) {
            String body = response.body();
            Jscode2sessionDto jscode2sessionDto = JSONUtil.toBean(body, Jscode2sessionDto.class);
            openid = jscode2sessionDto.getOpenid();
        } catch (Exception e) {
            logger.error("请求https://api.weixin.qq.com失败。", e);
            return new ResultDto<>(400, "获取openid失败。");
        }
        //根据openid查询
        queryWrapper.clear();
        queryWrapper.eq("openid", openid);
        UserInfo openidUserInfo = userInfoService.getOne(queryWrapper);
        if (Objects.isNull(openidUserInfo)) {
            userNameUserInfo.setOpenid(openid);
            userInfoService.updateById(userNameUserInfo);
        } else {
            //删除openidUserInfo
            userInfoService.removeById(openidUserInfo.getId());
            userNameUserInfo.setOpenid(openid);
            userInfoService.updateById(userNameUserInfo);
            //获取所拥有的角色
            QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
            userRoleQueryWrapper.eq("status", Constants.STATUS_EFFECT)
                    .eq("user_id", openidUserInfo.getId());
            List<UserRole> userRoleList = userRoleService.list(userRoleQueryWrapper);
            userRoleList.forEach(it -> {
                //查询角色是否已经存在
                QueryWrapper<UserRole> existRoleWrapper = new QueryWrapper<>();
                existRoleWrapper.eq("user_id", accountUserId).eq("role_id", it.getRoleId());
                List<UserRole> list = userRoleService.list(existRoleWrapper);
                if (CollectionUtil.isEmpty(list)) {
                    UserRole userRole = new UserRole();
                    userRole.setUserId(accountUserId);
                    userRole.setRoleId(it.getRoleId());
                    userRole.setStatus(Constants.STATUS_EFFECT);
                    userRole.setInputUser(UserUtil.getUserId());
                    userRole.setUpdateUser(UserUtil.getUserId());
                    userRoleService.save(userRole);
                }
            });
            userRoleService.removeByIds(userRoleList.stream().map(UserRole::getId).collect(Collectors.toList()));
        }
        return new ResultDto<>(200, "success");
    }
}
