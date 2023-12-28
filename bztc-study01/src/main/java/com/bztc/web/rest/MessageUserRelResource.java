package com.bztc.web.rest;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bztc.domain.MessageUserRel;
import com.bztc.dto.ResultDto;
import com.bztc.service.MessageUserRelService;
import com.bztc.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author daism
 * @create 2023-04-10 16:54
 * @description 个人消息用户关联resource层
 */
@RestController
@RequestMapping("/api/messageuserrelresource")
@Slf4j
public class MessageUserRelResource {
    @Autowired
    MessageUserRelService messageUserRelService;

    /**
     * 查询个人消息角色列表
     *
     * @return 个人消息列表
     */
    @GetMapping("/querybypage")
    public ResultDto<List<Map<String, Object>>> querybypage(@RequestParam("param") String params) {
        log.info("分页查询入参：{}", params);
        JSONObject jsonObject = JSONUtil.parseObj(params);

        Page<MessageUserRel> queryPage = new Page<>((int) jsonObject.get("pageIndex"), (int) jsonObject.get("pageSize"));

        MessageUserRel messageUserRel = new MessageUserRel();
        messageUserRel.setUserId(Integer.parseInt(Objects.requireNonNull(UserUtil.getUserId())));
        messageUserRel.setOperateStatus(jsonObject.get("operateStatus").toString());
        Page<Map<String, Object>> websiteListPage = this.messageUserRelService.selectPage(queryPage, messageUserRel);
        return new ResultDto<>(websiteListPage.getTotal(), websiteListPage.getRecords());
    }

    /**
     * 查询每个操作状态对应的数量
     *
     * @return
     */
    @GetMapping("/selectoperatecountbyuserid")
    public ResultDto<Map<String, Integer>> selectOperateCountByUserId() {
        return new ResultDto<>(this.messageUserRelService.selectOperateCountByUserId(UserUtil.getUserId()));
    }

    /**
     * 更改数据状态
     *
     * @return
     */
    @PostMapping("/allchangestatus")
    public ResultDto<String> allChangeStatus(@RequestBody Map<String, String> request) {
        this.messageUserRelService.allChangeStatus(request.get("operateStatus"), UserUtil.getUserId());
        return new ResultDto<>("success");
    }

    /**
     * 更改单个数据的状态
     *
     * @return
     */
    @PostMapping("/updateoperatestatus")
    public ResultDto<String> updateOperatestatus(@RequestBody Map<String, String> request) {
        Assert.notBlank(request.get("messageId"), "消息id不能为空");
        Assert.notBlank(request.get("operateStatus"), "更改状态不能为空");
        this.messageUserRelService.updateOperatestatus(request.get("messageId"), request.get("operateStatus"), UserUtil.getUserId());
        return new ResultDto<>("success");
    }
}
