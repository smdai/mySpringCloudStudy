package com.bztc.web.rest;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bztc.domain.AuthApply;
import com.bztc.dto.ResultDto;
import com.bztc.enumeration.ApplyTypeFlowEnum;
import com.bztc.enumeration.ObjectTypeEnum;
import com.bztc.service.AuthApplyService;
import com.bztc.service.FlowApplyService;
import com.bztc.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author daism
 * @create 2024-03-21 15:41
 * @description 权限申请resource层
 */
@RestController
@RequestMapping("/api/authapplyresource")
@Slf4j
public class AuthApplyResource {
    @Autowired
    private AuthApplyService authApplyService;
    @Autowired
    private FlowApplyService flowApplyService;

    /**
     * 描述：新增
     *
     * @return com.bztc.dto.ResultDto<com.bztc.entity.userInfo>
     * @author daism
     * @date 2022-09-29 10:35:10
     */
    @PostMapping("/insert")
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public ResultDto<AuthApply> insert(@RequestBody AuthApply authApply) {
        log.info("新增入参：{}", JSONUtil.toJsonStr(authApply));

        //判断是否有在途
        if (flowApplyService.isApplyingFlow(ObjectTypeEnum.AUTH_TYPE.key, Integer.parseInt(Objects.requireNonNull(UserUtil.getUserId())))) {
            return new ResultDto<>(400, "有在途流程，请勿重复申请。");
        }

        authApply.setInputUser(UserUtil.getUserId());
        authApply.setUpdateUser(UserUtil.getUserId());
        this.authApplyService.save(authApply);

        //初始化流程
        flowApplyService.initFlow(ApplyTypeFlowEnum.AUTH_APPLY.flowNo, ApplyTypeFlowEnum.AUTH_APPLY.applyType, ObjectTypeEnum.AUTH_TYPE.key, authApply.getId());
        return new ResultDto<>(authApply);
    }

    /**
     * 描述：分页查询
     *
     * @return java.util.List<com.bztc.entity.WebsiteList>
     * @author daism
     * @date 2022-09-28 14:52:04
     */
    @GetMapping("/querybypage")
    public ResultDto<List<Map<String, Object>>> queryByPage(@RequestParam("param") String params) {
        log.info("分页查询入参：{}", params);
        Map<String, Object> map = JSONUtil.toBean(params, Map.class);
        Page<Map<String, Object>> queryPage = new Page<>((int) map.get("pageIndex"), (int) map.get("pageSize"));
        map.put("inputUser", UserUtil.getUserId());
        Page<Map<String, Object>> page = this.authApplyService.selectPage(queryPage, map);
        return new ResultDto<>(page.getTotal(), page.getRecords());
    }

    /**
     * 描述：编辑-更新
     *
     * @return com.bztc.dto.ResultDto<com.bztc.entity.userInfo>
     * @author daism
     * @date 2022-10-14 09:55:17
     */
    @PostMapping("/update")
    public ResultDto<AuthApply> update(@RequestBody AuthApply authApply) {
        log.info("更新入参：{}", JSONUtil.toJsonStr(authApply));
        //判断是否在申请阶段
        if (!flowApplyService.isApplyPhase(ObjectTypeEnum.AUTH_TYPE.key, authApply.getId())) {
            return new ResultDto<>(400, "非申请阶段，不可修改。");
        }

        ResultDto<AuthApply> resultDto = new ResultDto<>();
        authApply.setUpdateUser(UserUtil.getUserId());
        try {
            this.authApplyService.updateById(authApply);
            resultDto.setCode(200);
            resultDto.setData(authApply);
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
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public ResultDto<Boolean> delete(@RequestBody AuthApply authApply) {
        log.info("删除入参：{}", JSONUtil.toJsonStr(authApply));
        //判断是否在申请阶段
        if (!flowApplyService.isApplyPhase(ObjectTypeEnum.AUTH_TYPE.key, authApply.getId())) {
            return new ResultDto<>(400, "非申请阶段，不可删除。");
        }
        this.flowApplyService.deleteFlow(ObjectTypeEnum.AUTH_TYPE.key, authApply.getId());
        return new ResultDto<>(this.authApplyService.removeById(authApply));
    }
}
