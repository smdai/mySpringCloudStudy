package com.bztc.web.rest;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bztc.domain.MessageRoleRel;
import com.bztc.domain.MessageUserRel;
import com.bztc.domain.PersonalMessage;
import com.bztc.dto.ResultDto;
import com.bztc.service.MessageRoleRelService;
import com.bztc.service.MessageUserRelService;
import com.bztc.service.PersonalMessageService;
import com.bztc.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author daism
 * @create 2023-04-10 16:54
 * @description 个人消息resource层
 */
@RestController
@RequestMapping("/api/personalmessageresource")
@Slf4j
public class PersonalMessageResource {
    @Autowired
    PersonalMessageService personalMessageService;
    @Autowired
    MessageRoleRelService messageRoleRelService;
    @Autowired
    MessageUserRelService messageUserRelService;

    /**
     * 查询个人消息列表
     *
     * @return 个人消息列表
     */
    @GetMapping("/querybypage")
    public ResultDto<List<PersonalMessage>> querybypage(@RequestParam("param") String params) {
        log.info("分页查询入参：{}", params);
        JSONObject jsonObject = JSONUtil.parseObj(params);

        Page<PersonalMessage> queryPage = new Page<>((int) jsonObject.get("pageIndex"), (int) jsonObject.get("pageSize"));

        QueryWrapper<PersonalMessage> queryWrapper = new QueryWrapper<>();
        if (!StrUtil.isBlankIfStr(jsonObject.get("messageHead"))) {
            queryWrapper.like("message_head", jsonObject.get("messageHead"));
        }
        if (!StrUtil.isBlankIfStr(jsonObject.get("messageBody"))) {
            queryWrapper.like("message_body", jsonObject.get("messageBody"));
        }
        queryWrapper.orderByDesc("id");
        Page<PersonalMessage> websiteListPage = this.personalMessageService.page(queryPage, queryWrapper);
        return new ResultDto<>(websiteListPage.getTotal(), websiteListPage.getRecords());
    }

    /**
     * 描述：新增
     *
     * @return com.bztc.dto.ResultDto<com.bztc.entity.userInfo>
     * @author daism
     * @date 2022-09-29 10:35:10
     */
    @PostMapping("/insert")
    public ResultDto<PersonalMessage> insert(@RequestBody PersonalMessage personalMessage) {
        log.info("新增入参：{}", JSONUtil.toJsonStr(personalMessage));
        ResultDto<PersonalMessage> resultDto = new ResultDto<>();
        personalMessage.setInputUser(UserUtil.getUserId());
        personalMessage.setUpdateUser(UserUtil.getUserId());
        try {
            this.personalMessageService.save(personalMessage);
            resultDto.setCode(200);
            resultDto.setData(personalMessage);
        } catch (Exception e) {
            log.error("插入数据库失败！", e);
            resultDto.setCode(400);
            resultDto.setMessage("插入数据库失败," + e.getMessage());
        }
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
    public ResultDto<PersonalMessage> update(@RequestBody PersonalMessage personalMessage) {
        log.info("更新入参：{}", JSONUtil.toJsonStr(personalMessage));
        ResultDto<PersonalMessage> resultDto = new ResultDto<>();
        personalMessage.setUpdateUser(UserUtil.getUserId());
        try {
            this.personalMessageService.updateById(personalMessage);
            resultDto.setCode(200);
            resultDto.setData(personalMessage);
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
    @Transactional(rollbackFor = Exception.class)
    public ResultDto<Integer> delete(@RequestBody PersonalMessage personalMessage) {
        log.info("删除入参：{}", JSONUtil.toJsonStr(personalMessage));
        ResultDto<Integer> resultDto = new ResultDto<>();
        this.personalMessageService.removeById(personalMessage);
        this.cancel(personalMessage);
        resultDto.setCode(200);
        return resultDto;
    }

    /**
     * 描述：根据主键撤回消息
     *
     * @return com.bztc.dto.ResultDto<java.lang.Integer>
     * @author daism
     * @date 2022-10-14 10:05:22
     */
    @PostMapping("/cancel")
    @Transactional(rollbackFor = Exception.class)
    public ResultDto<Integer> cancel(@RequestBody PersonalMessage personalMessage) {
        log.info("根据主键撤回消息入参：{}", JSONUtil.toJsonStr(personalMessage));
        ResultDto<Integer> resultDto = new ResultDto<>();
        QueryWrapper<MessageRoleRel> messageRoleRelQueryWrapper = new QueryWrapper<>();
        messageRoleRelQueryWrapper.eq("message_id", personalMessage.getId());
        this.messageRoleRelService.remove(messageRoleRelQueryWrapper);
        QueryWrapper<MessageUserRel> messageUserRelQueryWrapper = new QueryWrapper<>();
        messageUserRelQueryWrapper.eq("message_id", personalMessage.getId());
        this.messageUserRelService.remove(messageUserRelQueryWrapper);
        resultDto.setCode(200);
        return resultDto;
    }
}
