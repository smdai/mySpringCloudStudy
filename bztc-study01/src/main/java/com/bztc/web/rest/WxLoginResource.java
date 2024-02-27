package com.bztc.web.rest;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bztc.constant.Constants;
import com.bztc.constant.RedisConstants;
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
import com.bztc.utils.DateUtil;
import com.bztc.utils.RedisUtil;
import com.bztc.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author daism
 * @create 2022-10-14 16:18
 * @description 微信小程序登录相关
 */
@RestController
@RequestMapping("/api/wxloginresource")
@Slf4j
public class WxLoginResource {
    @Autowired
    public UserInfoService userInfoService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private RedisUtil redisUtil;
    @Value("${wx.applet.appId}")
    private String appId;
    @Value("${wx.applet.appSecret}")
    private String appSecret;
    @Value("${bztc.defult.avatar.path}")
    private String defultAvatarPath;
    @Value("${bztc.miniProgram.qrcode.dir.url}")
    private String miniProgramQrCodeUrl;
    @Value("${wx.applet.envVersion:release}")
    private String envVersion;
    @Value("${wx.applet.wxWebsiteLogin.toPageUrl}")
    private String toPageUrl;

    /**
     * 获取Jscode2sessionDto
     *
     * @return
     */
    @GetMapping("/getjscode2session")
    @Transactional
    public ResultDto<SessionInfoDto> getJscode2session(@RequestParam("code") String code, @RequestParam("scene") String scene) {
        SessionInfoDto sessionInfoDto = new SessionInfoDto();
        String openid = "";
        HttpRequest get = HttpUtil.createGet(
                "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code");
        try (HttpResponse response = get.execute()) {
            String body = response.body();
            Jscode2sessionDto jscode2sessionDto = JSONUtil.toBean(body, Jscode2sessionDto.class);
            openid = jscode2sessionDto.getOpenid();
        } catch (Exception e) {
            log.error("请求https://api.weixin.qq.com失败。", e);
            return new ResultDto<>();
        }
        //判断是否新用户
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        UserInfo userInfo = userInfoService.getOne(queryWrapper);
        if (Objects.nonNull(userInfo)) {
            //老用户
            log.info("老用户：{}", openid);
        } else {
            log.info("新用户：{}", openid);
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
                log.error("用户名：【{}】已被注册，请更换其他用户名。", userInfo.getUserName(), e);
                return new ResultDto<>(400, "用户名：【" + userInfo.getUserName() + "】已被注册，请更换其他用户名。");
            } catch (Exception e) {
                log.error("注册异常。", e);
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
        if (!"bztc".equals(scene)) {
            Object o = redisUtil.get(RedisConstants.WX_WEBSITE_LOGIN_FLAG + scene);
            if (Objects.isNull(o)) {
                return new ResultDto<>(400, "登录二维码已失效。");
            }
            redisUtil.set(RedisConstants.WX_WEBSITE_LOGIN_FLAG + scene, "1", 60);
            redisUtil.set(RedisConstants.WX_WEBSITE_LOGIN_INFO + scene, JSONUtil.toJsonStr(sessionInfoDto), 60);
        }
        return new ResultDto<>(sessionInfoDto);
    }

    /**
     * 查询是否已扫码登录
     *
     * @param scene
     * @return
     */
    @GetMapping("/getwxwebsitesession")
    public ResultDto<SessionInfoDto> getWxWebsiteSession(@RequestParam("scene") String scene) {
        Object o = redisUtil.get(RedisConstants.WX_WEBSITE_LOGIN_FLAG + scene);
        if (Objects.isNull(o)) {
            return new ResultDto<>(401, "登录二维码已失效。");
        }
        if (Constants.STATUS_NOAVAIL.equals(String.valueOf(o))) {
            return new ResultDto<>(400, "用户还未扫码登录。");
        }
        //用户已登录
        if (Constants.STATUS_EFFECT.equals(String.valueOf(o))) {
            Object sessionObject = redisUtil.get(RedisConstants.WX_WEBSITE_LOGIN_INFO + scene);
            if (Objects.isNull(sessionObject)) {
                return new ResultDto<>(401, "登录信息已失效。");
            }
            return new ResultDto<>(JSONUtil.toBean(sessionObject.toString(), SessionInfoDto.class));
        }
        return new ResultDto<>(400, "未知错误。");
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
            log.error("请求https://api.weixin.qq.com失败。", e);
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

    /**
     * 获取小程序登录二维码
     *
     * @return
     */
    @GetMapping("/getminiprogramqrcode")
    public ResultDto<String> getMiniProgramQrCode(@RequestParam("scene") String scene) {
        log.info("scene:{}", scene);
        String accessToken = "";
        Object accessTokenObject = redisUtil.get(RedisConstants.WX_ACCESS_TOKEN_KEY);
        if (Objects.nonNull(accessTokenObject)) {
            accessToken = String.valueOf(accessTokenObject);
        } else {
            HttpRequest get = HttpUtil.createGet(
                    "https://api.weixin.qq.com/cgi-bin/token?appid=" + appId + "&secret=" + appSecret + "&grant_type=client_credential");
            try (HttpResponse response = get.execute()) {
                String body = response.body();
                log.info("body:{}", body);
                Map accessTokenMap = JSONUtil.toBean(body, Map.class);
                redisUtil.set(RedisConstants.WX_ACCESS_TOKEN_KEY, accessTokenMap.get("access_token"), Long.parseLong(String.valueOf(accessTokenMap.get("expires_in"))));
                accessToken = String.valueOf(accessTokenMap.get("access_token"));
            } catch (Exception e) {
                log.error("请求https://api.weixin.qq.com/cgi-bin/token失败。", e);
                return new ResultDto<>(400, "获取access_token失败。");
            }
        }

        Map<String, Object> postBody = new HashMap<>();
        postBody.put("scene", scene);
        postBody.put("page", toPageUrl);
        postBody.put("env_version", envVersion);
        byte[] bytes;
        try (HttpResponse execute = HttpRequest.post("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken).body(JSONUtil.toJsonStr(postBody)).execute()) {
            bytes = execute.bodyBytes();
        } catch (Exception e) {
            log.error("请求https://api.weixin.qq.com/wxa/getwxacodeunlimit失败。", e);
            return new ResultDto<>(400, "获取小程序二维码失败。");
        }
        String fileName = DateUtil.getNowTimeStr(DateUtil.PATTERN_YYYYMMDDHHMMSS) + "_" + RandomUtil.randomNumbers(10) + ".jpeg";
        Path directoryIndex = Paths.get(miniProgramQrCodeUrl);
        // 使用 Files.createDirectories 创建目录，如果目录已存在则不会抛出异常
        try {
            Files.createDirectories(directoryIndex);
            FileUtil.writeBytes(bytes, miniProgramQrCodeUrl + fileName);
            redisUtil.set(RedisConstants.WX_WEBSITE_LOGIN_FLAG + scene, "0", 60);
            return new ResultDto<>(Constants.IMAGE_PREFIX + miniProgramQrCodeUrl + fileName);
        } catch (IOException e) {
            log.error("保存小程序二维码失败。", e);
            return new ResultDto<>(400, "保存小程序二维码失败");
        }
    }
}
