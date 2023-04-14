package com.bztc.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author daism
 * @create 2023-04-06 11:56
 * @description es索引工具类
 */
public class EsIndexUtil {

    /**
     * 根据录入日期获取年月索引
     * @param indexPre
     * @return
     */
    public static String getYearMonthIndexByInputDate(String indexPre, LocalDate date){
        return indexPre + date.format(DateTimeFormatter.ofPattern("yyyy.MM"));
    }

    /**
     * 获取年月索引
     * @param indexPre
     * @return
     */
    public static String getYearMonthIndex(String indexPre){
        return indexPre + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM"));
    }

    /**
     * 获取全匹配索引
     * @param indexPre
     * @return
     */
    public static String getAllIndex(String indexPre){
        return indexPre + "*";
    }
}
