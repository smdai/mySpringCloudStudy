package com.bztc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bztc.domain.MenuInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author daishuming
 * @description 针对表【menu_info(菜单信息表)】的数据库操作Mapper
 * @createDate 2022-10-17 17:07:28
 * @Entity com.bztc.domain.MenuInfo
 */
@Mapper
public interface MenuInfoMapper extends BaseMapper<MenuInfo> {

}




