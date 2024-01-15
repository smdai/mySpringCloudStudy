package com.bztc.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author daism
 * @create 2023-03-29 16:35
 * @description 日期工具类
 */
public class DateUtil {
    public static final String PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String PATTERN_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**
     * 字符串转日期
     *
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     * @return 日期
     */
    public static LocalDate getLocalDate(String dateStr, String pattern) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间
     */
    public static String getLocalTimeNowStr() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(PATTERN_YYYY_MM_DD_HH_MM_SS));
    }

    /**
     * 获取当前时间字符串
     *
     * @return 当前时间
     */
    public static String getNowTimeStr(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间
     */
    public static String getLocalDateNowStr() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(PATTERN_YYYY_MM_DD));
    }
}
