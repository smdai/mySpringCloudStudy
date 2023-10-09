package com.bztc.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author daism
 * @create 2023-09-26 10:27
 * @description test
 */
public class Test {
    public static void main(String[] args) {
        List<Long> list = new ArrayList<>();
//        list.add(1L);
//        list.add((long) 111.11);
        System.out.println(list);
        Object[] objects = list.toArray();
        String s = null;
        BigDecimal b = new BigDecimal(s);
        System.out.println(Arrays.toString(objects));
    }
}
