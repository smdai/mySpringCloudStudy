package com.bztc.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author daism
 * @create 2023-10-11 15:56
 * @description 菜单dto
 */
@Data
public class MenuTreeDto implements Serializable {
    private static final long serialVersionUID = -4806123634016953794L;
    private int menuId;
    private String menuName;
    private List<MenuTreeDto> children;
    private String label;
    private int level;
}
