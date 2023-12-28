package com.bztc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bztc.domain.MessageUserRel;

import java.util.Map;

/**
 * @author daishuming
 * @description 针对表【message_user_rel(个人消息与用户关联表)】的数据库操作Service
 * @createDate 2023-12-11 19:08:03
 */
public interface MessageUserRelService extends IService<MessageUserRel> {
    Page<Map<String, Object>> selectPage(Page<MessageUserRel> page, MessageUserRel messageUserRel);

    Map<String, Integer> selectOperateCountByUserId(String userId);

    void allChangeStatus(String operateStatus, String userId);

    void updateOperatestatus(String messageId, String operateStatus, String userId);
}
