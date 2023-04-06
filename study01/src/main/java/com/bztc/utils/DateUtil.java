package com.bztc.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author daism
 * @create 2023-03-29 16:35
 * @description 日期工具类
 */
public class DateUtil {
    public static String getIndexCurrentDay(){
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    public static void main(String[] args) {
        System.out.println(getIndexCurrentDay());
    }
}
