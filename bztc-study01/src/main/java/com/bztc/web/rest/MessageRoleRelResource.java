package com.bztc.web.rest;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bztc.constant.Constants;
import com.bztc.domain.MessageRoleRel;
import com.bztc.domain.MessageUserRel;
import com.bztc.domain.UserRole;
import com.bztc.dto.MessageRoleRelDto;
import com.bztc.dto.ResultDto;
import com.bztc.service.MessageRoleRelService;
import com.bztc.service.MessageUserRelService;
import com.bztc.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author daism
 * @create 2023-04-10 16:54
 * @description 个人消息角色关联resource层
 */
@RestController
@RequestMapping("/api/messagerolerelresource")
@Slf4j
public class MessageRoleRelResource {
    @Autowired
    MessageRoleRelService messageRoleRelService;

    @Autowired
    MessageUserRelService messageUserRelService;

    @Autowired
    UserRoleService userRoleService;

    /**
     * 查询个人消息角色列表
     *
     * @return 个人消息列表
     */
    @GetMapping("/querybypage")
    public ResultDto<List<MessageRoleRel>> querybypage(@RequestParam("param") String params) {
        log.info("分页查询入参：{}", params);
        JSONObject jsonObject = JSONUtil.parseObj(params);

        Page<MessageRoleRel> queryPage = new Page<>((int) jsonObject.get("pageIndex"), (int) jsonObject.get("pageSize"));

        QueryWrapper<MessageRoleRel> queryWrapper = new QueryWrapper<>();
        if (!StrUtil.isBlankIfStr(jsonObject.get("messageId"))) {
            queryWrapper.eq("message_id", jsonObject.get("messageId"));
        }
        if (!StrUtil.isBlankIfStr(jsonObject.get("roleId"))) {
            queryWrapper.eq("role_id", jsonObject.get("roleId"));
        }
        if (!StrUtil.isBlankIfStr(jsonObject.get("sendStatus"))) {
            queryWrapper.eq("send_status", jsonObject.get("sendStatus"));
        }
        queryWrapper.orderByDesc("id");
        Page<MessageRoleRel> websiteListPage = this.messageRoleRelService.page(queryPage, queryWrapper);
        return new ResultDto<>(websiteListPage.getTotal(), websiteListPage.getRecords());
    }

    /**
     * 描述：推送消息
     *
     * @return com.bztc.dto.ResultDto<com.bztc.entity.userInfo>
     * @author daism
     * @date 2022-09-29 10:35:10
     */
    @PostMapping("/insert")
    @Transactional(rollbackFor = Exception.class)
    public ResultDto<MessageRoleRel> insert(@RequestBody MessageRoleRelDto messageRoleRelDto) {
        log.info("新增入参：{}", JSONUtil.toJsonStr(messageRoleRelDto));
        Assert.notNull(messageRoleRelDto, "入参不能为空");
        Assert.notNull(messageRoleRelDto.getMessageId(), "messageId不能为空");
        Assert.isFalse(CollectionUtil.isEmpty(messageRoleRelDto.getRoleIds()), "关联角色id不能为空");
        ResultDto<MessageRoleRel> resultDto = new ResultDto<>();
        List<MessageRoleRel> messageRoleRels = messageRoleRelDto.getRoleIds().stream().map(it -> {
            MessageRoleRel messageRoleRel = new MessageRoleRel();
            messageRoleRel.setMessageId(messageRoleRelDto.getMessageId());
            messageRoleRel.setRoleId(it);
            messageRoleRel.setSendStatus(Constants.SEND_STATUS_1);
            return messageRoleRel;
        }).collect(Collectors.toList());
        this.messageRoleRelService.saveBatch(messageRoleRels);
        //查询角色关联的用户
        List<UserRole> userRoleList = this.userRoleService.selectByRoleIds(messageRoleRelDto.getRoleIds());
        List<MessageUserRel> messageUserRels = userRoleList.stream().map(it -> {
            MessageUserRel messageUserRel = new MessageUserRel();
            messageUserRel.setMessageId(messageRoleRelDto.getMessageId());
            messageUserRel.setUserId(it.getUserId());
            messageUserRel.setOperateStatus("1");
            messageUserRel.setNoteTime(new Date());
            return messageUserRel;
        }).collect(Collectors.toList());
        this.messageUserRelService.saveBatch(messageUserRels);
        resultDto.setCode(200);
        return resultDto;
    }

    /**
     * 描述：编辑-更新
     *
     * @return com.bztc.dto.ResultDto<com.bztc.entity.userInfo>
     * @author daism
     * @date 2022-10-14 09:55:17
     */
    @PostMapping("/update")
    public ResultDto<MessageRoleRel> update(@RequestBody MessageRoleRel messageRoleRel) {
        log.info("更新入参：{}", JSONUtil.toJsonStr(messageRoleRel));
        ResultDto<MessageRoleRel> resultDto = new ResultDto<>();
        try {
            this.messageRoleRelService.updateById(messageRoleRel);
            resultDto.setCode(200);
            resultDto.setData(messageRoleRel);
        } catch (Exception e) {
            log.error("更新数据库失败！", e);
            resultDto.setCode(400);
            resultDto.setMessage("更新数据库失败," + e.getMessage());
        }
        return resultDto;
    }

    /**
     * 描述：根据主键删除
     *
     * @return com.bztc.dto.ResultDto<java.lang.Integer>
     * @author daism
     * @date 2022-10-14 10:05:22
     */
    @PostMapping("/delete")
    public ResultDto<Integer> delete(@RequestBody MessageRoleRel messageRoleRel) {
        log.info("删除入参：{}", JSONUtil.toJsonStr(messageRoleRel));
        ResultDto<Integer> resultDto = new ResultDto<>();
        this.messageRoleRelService.removeById(messageRoleRel);
        resultDto.setCode(200);
        return resultDto;
    }
}
