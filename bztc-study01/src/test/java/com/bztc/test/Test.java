package com.bztc.test;

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
        String s = "10001,20001,30001";
        System.out.println(Arrays.asList(s.split(",", -1)).contains("1"));
        List<String> longList = new ArrayList<>();
        longList.add("1");
        longList.add("4");
        longList.add("1000");
        String[] strings = longList.toArray(new String[0]);

        System.out.println(Arrays.toString(strings));
    }
}
