package com.bztc.web.rest;

import cn.hutool.core.img.Img;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.bztc.constant.Constants;
import com.bztc.domain.ImageInfo;
import com.bztc.domain.UserInfo;
import com.bztc.dto.ResultDto;
import com.bztc.enumeration.FileBusinessTypeEnum;
import com.bztc.service.ImageInfoService;
import com.bztc.service.UserInfoService;
import com.bztc.utils.DateUtil;
import com.bztc.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
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
    @Autowired
    ImageInfoService imageInfoService;
    @Value("${bztc.avatar.dir.url}")
    private String avatarUrl;
    @Value("${bztc.record.img.dir.url}")
    private String recordImgUrl;

    @Value("${bztc.record_index.img.dir.url}")
    private String recordIndexImgUrl;

    /**
     * 上传头像
     *
     * @return
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

    /**
     * 上传记录图片
     *
     * @return
     */
    @PostMapping("/uploadrecordimg")
    public ResultDto<Integer> uploadRecordImg(@RequestBody MultipartFile file) {
        String dirUrl = recordImgUrl + UserUtil.getUserId() + "/";
        String dirIndexUrl = recordIndexImgUrl + UserUtil.getUserId() + "/";
        //获取图片原文件名
        log.info("记录图片上传开始。");
        if (file.isEmpty()) {
            return new ResultDto<>(400, "请选择文件。");
        }
        // 获取文件名和字节数组
        String fileName = DateUtil.getNowTimeStr(DateUtil.PATTERN_YYYYMMDDHHMMSS) + "_" + RandomUtil.randomNumbers(10) + "_" + file.getOriginalFilename();
        String fileIndexName = fileName + ".jpg";
        //路径入表
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setName(fileName);
        imageInfo.setType(FileBusinessTypeEnum.IMAGE_RECORD.key);
        imageInfo.setStatus(Constants.STATUS_EFFECT);
        imageInfo.setInputUser(UserUtil.getUserId());
        imageInfo.setUrl(Constants.IMAGE_PREFIX + dirUrl + fileName);
        imageInfo.setUrlIndex(Constants.IMAGE_PREFIX + dirIndexUrl + fileIndexName);
        imageInfoService.save(imageInfo);
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
            // 使用 Paths.get 创建 Path 对象
            Path directoryIndex = Paths.get(dirIndexUrl);

            // 使用 Files.createDirectories 创建目录，如果目录已存在则不会抛出异常
            Files.createDirectories(directoryIndex);

            Img.from(targetFile)
                    .setQuality(0.1)
                    .write(FileUtil.file(dirIndexUrl + fileIndexName));

            return new ResultDto<>(200, "上传记录图片成功。", imageInfo.getId());
        } catch (IOException e) {
            log.error("上传图片失败。", e);
            return new ResultDto<>(400, "上传记录图片失败。");
        }

    }
}
