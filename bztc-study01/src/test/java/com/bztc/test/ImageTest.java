package com.bztc.test;

import cn.hutool.core.img.Img;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;

import java.awt.*;

/**
 * @author daism
 * @create 2024-02-05 18:02
 * @description 图片测试
 */
public class ImageTest {
    public static void main(String[] args) {

        boolean write = Img.from(FileUtil.file("/Users/daishuming/Downloads/filetemp/recordImg/1/20240115181359_8660670119_壁纸.jpg"))
                .setQuality(0.1)//压缩比率
                .write(FileUtil.file("/Users/daishuming/Downloads/filetemp/recordImg/1111333_target.jpg"));
        Image image = Toolkit.getDefaultToolkit().getImage("/Users/daishuming/Downloads/filetemp/recordImg/1111333_target.jpg");
        String jpg = ImgUtil.toBase64DataUri(image, "jpg");
        System.out.println(jpg);
    }
}
