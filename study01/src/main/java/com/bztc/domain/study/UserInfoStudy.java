package com.bztc.domain.study;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author daism
 * @create 2023-03-29 16:45
 * @description 学习-用户信息
 */
@Data
public class UserInfoStudy {
    private String id;
    private String name;
    private String sex;
    private int age;
    private String phone;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate inputDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate updateDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime inputTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
