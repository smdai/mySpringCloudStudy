package com.bztc.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author daism
 * @create 2023-12-16 16:17
 * @description 个人消息与角色关联dto
 */
@Data
public class MessageRoleRelDto implements Serializable {
    private static final long serialVersionUID = -6733621222134146341L;
    private Integer messageId;
    private List<Integer> roleIds;
}
