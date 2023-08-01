package com.bztc.dto.study;

import com.bztc.dto.QueryCommonDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author daism
 * @create 2023-04-07 16:09
 * @description 用户信息dto
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfoStudyDto extends QueryCommonDto {

    private static final long serialVersionUID = -6903562897891442961L;

    private String id;
    private String name;
    private String sex;
    private Integer age;
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
