package com.bztc.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author daism
 * @create 2024-03-12 15:03
 * @description 通用工具
 */
public class CommonUtil {
    /**
     * 是否有效手机号
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        // 校验手机号格式，包括11位数字，且以1开头
        String phoneRegex = "^1[3-9]\\d{9}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
