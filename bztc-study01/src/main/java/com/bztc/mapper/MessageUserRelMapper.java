package com.bztc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bztc.domain.MessageUserRel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author daishuming
 * @description 针对表【message_user_rel(个人消息与用户关联表)】的数据库操作Mapper
 * @createDate 2023-12-11 19:08:03
 * @Entity com.bztc.domain.MessageUserRel
 */
@Mapper
public interface MessageUserRelMapper extends BaseMapper<MessageUserRel> {
    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    Page<Map<String, Object>> selectByPage(IPage<MessageUserRel> page, @Param("operateStatus") String operateStatus, @Param("userId") Integer userId);

    List<Map<String, Object>> selectOperateCountByUserId(@Param("userId") String userId);
}




