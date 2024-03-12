package com.bztc.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author daism
 * @create 2024-03-12 10:19
 * @description 网络相关工具
 */
public class NetWorkUtil {
    /**
     * 校验是否有效ip地址
     *
     * @param ipAddress
     * @return
     */
    public static boolean isValidIpAddress(String ipAddress) {
        String ipRegex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        Pattern pattern = Pattern.compile(ipRegex);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }
}
