package com.bztc.web.rest;

import cn.hutool.core.util.RandomUtil;
import com.bztc.constant.Constants;
import com.bztc.domain.UserInfo;
import com.bztc.dto.ResultDto;
import com.bztc.service.UserInfoService;
import com.bztc.utils.DateUtil;
import com.bztc.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author daism
 * @create 2022-10-17 15:35
 * @description 文件处理controller层
 */
@RestController
@RequestMapping("/api/fileresource")
@Slf4j
public class FileResource {

    @Autowired
    UserInfoService userInfoService;
    @Value("${bztc.avatar.dir.url}")
    private String avatarUrl;

    /**
     * 上传头像
     *
     * @return 用户
     */
    @PostMapping("/uploadavatarimg")
    public ResultDto<String> uploadAvatarImg(@RequestParam("file") MultipartFile file) {
        String dirUrl = avatarUrl + UserUtil.getUserId() + "/";
        //获取图片原文件名
        log.info("文件上传开始。");
        if (file.isEmpty()) {
            return new ResultDto<>(400, "请选择文件。");
        }
        // 获取文件名和字节数组
        String fileName = DateUtil.getNowTimeStr(DateUtil.PATTERN_YYYYMMDDHHMMSS) + "_" + RandomUtil.randomNumbers(10) + "_" + file.getOriginalFilename();
        //路径入表
        UserInfo userInfo = new UserInfo();
        userInfo.setId(Integer.parseInt(Objects.requireNonNull(UserUtil.getUserId())));
        userInfo.setAvatarUrl(Constants.IMAGE_PREFIX + dirUrl + fileName);
        userInfoService.updateById(userInfo);
        try {
            // 使用 Paths.get 创建 Path 对象
            Path directory = Paths.get(dirUrl);

            // 使用 Files.createDirectories 创建目录，如果目录已存在则不会抛出异常
            Files.createDirectories(directory);

            byte[] bytes = file.getBytes();
            // 创建目标文件
            File targetFile = new File(dirUrl + fileName);

            // 写入文件内容
            try (FileOutputStream fos = new FileOutputStream(targetFile)) {
                fos.write(bytes);
            }
            return new ResultDto<>(200, "上传头像成功。");
        } catch (IOException e) {
            log.error("上传头像失败。", e);
            return new ResultDto<>(400, "上传头像失败。");
        }
    }
}
