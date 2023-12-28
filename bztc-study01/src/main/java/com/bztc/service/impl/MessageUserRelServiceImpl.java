package com.bztc.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bztc.domain.MessageUserRel;
import com.bztc.mapper.MessageUserRelMapper;
import com.bztc.service.MessageUserRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author daishuming
 * @description 针对表【message_user_rel(个人消息与用户关联表)】的数据库操作Service实现
 * @createDate 2023-12-11 19:08:03
 */
@Service
public class MessageUserRelServiceImpl extends ServiceImpl<MessageUserRelMapper, MessageUserRel>
        implements MessageUserRelService {
    @Autowired
    MessageUserRelMapper messageUserRelMapper;

    /**
     * @param page
     * @param messageUserRel
     * @return
     */
    @Override
    public Page<Map<String, Object>> selectPage(Page<MessageUserRel> page, MessageUserRel messageUserRel) {
        return messageUserRelMapper.selectByPage(page, messageUserRel.getOperateStatus(), messageUserRel.getUserId());
    }

    /**
     * 查询每个操作状态对应的数量
     *
     * @param userId
     * @return
     */
    @Override
    public Map<String, Integer> selectOperateCountByUserId(String userId) {
        Map<String, Integer> returnMap = new HashMap<>();
        List<Map<String, Object>> maps = this.messageUserRelMapper.selectOperateCountByUserId(userId);
        if (CollectionUtil.isEmpty(maps)) {
            returnMap.put("unreadCount", 0);
            returnMap.put("readCount", 0);
            returnMap.put("recycleCount", 0);
            return returnMap;
        }
        maps.forEach(it -> {
            if ("1".equals(String.valueOf(it.get("operateStatus")))) {
                returnMap.put("unreadCount", Integer.parseInt(String.valueOf(it.get("count"))));
            }
            if ("2".equals(String.valueOf(it.get("operateStatus")))) {
                returnMap.put("readCount", Integer.parseInt(String.valueOf(it.get("count"))));
            }
            if ("3".equals(String.valueOf(it.get("operateStatus")))) {
                returnMap.put("recycleCount", Integer.parseInt(String.valueOf(it.get("count"))));
            }
        });
        if (Objects.isNull(returnMap.get("unreadCount"))) {
            returnMap.put("unreadCount", 0);
        }
        if (Objects.isNull(returnMap.get("readCount"))) {
            returnMap.put("readCount", 0);
        }
        if (Objects.isNull(returnMap.get("recycleCount"))) {
            returnMap.put("recycleCount", 0);
        }
        return returnMap;
    }

    /**
     * 更改所以数据状态
     *
     * @param userId
     */
    @Override
    public void allChangeStatus(String operateStatus, String userId) {
        if ("del".equals(operateStatus)) {
            QueryWrapper<MessageUserRel> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId).eq("operate_status", "3");
            this.messageUserRelMapper.delete(queryWrapper);
            return;
        }
        MessageUserRel messageUserRel = new MessageUserRel();
        messageUserRel.setOperateStatus(operateStatus);
        QueryWrapper<MessageUserRel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("operate_status", Integer.parseInt(operateStatus) - 1);
        this.messageUserRelMapper.update(messageUserRel, queryWrapper);
    }

    /**
     * 更改状态
     *
     * @param messageId
     * @param operateStatus
     * @param userId
     */
    @Override
    public void updateOperatestatus(String messageId, String operateStatus, String userId) {
        MessageUserRel messageUserRel = new MessageUserRel();
        messageUserRel.setOperateStatus(operateStatus);
        QueryWrapper<MessageUserRel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("message_id", messageId).eq("operate_status", Integer.parseInt(operateStatus) - 1);
        this.messageUserRelMapper.update(messageUserRel, queryWrapper);
    }
}




