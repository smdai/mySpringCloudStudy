package com.bztc.web.rest;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bztc.constant.Constants;
import com.bztc.domain.ControlInfo;
import com.bztc.domain.MenuInfo;
import com.bztc.dto.ResultDto;
import com.bztc.service.AuthResContrService;
import com.bztc.service.ControlInfoService;
import com.bztc.service.MenuInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author daism
 * @create 2022-10-17 15:35
 * @description 控制点管理controller层
 */
@RestController
@RequestMapping("/api/controlinforesource")
@Slf4j
public class ControlInfoResource {
    @Autowired
    private ControlInfoService controlInfoService;
    @Autowired
    private AuthResContrService authResContrService;
    @Autowired
    private MenuInfoService menuInfoService;

    /**
     * 查询控制点列表
     *
     * @return 用户
     */
    @GetMapping("/querybypage")
    public ResultDto<List<ControlInfo>> querybypage(@RequestParam("param") String params) {
        log.info("分页查询入参：{}", params);
        JSONObject jsonObject = JSONUtil.parseObj(params);

        Page<ControlInfo> queryPage = new Page<>((int) jsonObject.get("pageIndex"), (int) jsonObject.get("pageSize"));

        QueryWrapper<ControlInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StrUtil.isBlankIfStr(jsonObject.get("controlKey")), "control_key", jsonObject.get("controlKey"))
                .like(!StrUtil.isBlankIfStr(jsonObject.get("controlName")), "control_name", jsonObject.get("controlName"))
                .like(!StrUtil.isBlankIfStr(jsonObject.get("controlUrl")), "control_url", jsonObject.get("controlUrl"))
                .eq(!StrUtil.isBlankIfStr(jsonObject.get("status")), "status", jsonObject.get("status"))
                .eq(!StrUtil.isBlankIfStr(jsonObject.get("menuId")), "menu_id", jsonObject.get("menuId"));
        queryWrapper.orderByDesc("control_id");
        Page<ControlInfo> websiteListPage = this.controlInfoService.page(queryPage, queryWrapper);
        websiteListPage.getRecords().forEach(it -> {
            MenuInfo menuInfo = menuInfoService.getById(it.getMenuId());
            it.setMenuName(menuInfo.getMenuName());
        });
        return new ResultDto<>(websiteListPage.getTotal(), websiteListPage.getRecords());
    }

    /**
     * 描述：新增用户
     *
     * @return com.bztc.dto.ResultDto<com.bztc.entity.controlInfo>
     * @author daism
     * @date 2022-09-29 10:35:10
     */
    @PostMapping("/insert")
    public ResultDto<ControlInfo> insert(@RequestBody ControlInfo controlInfo) {
        log.info("新增入参：{}", JSONUtil.toJsonStr(controlInfo));
        ResultDto<ControlInfo> resultDto = new ResultDto<>();
        controlInfo.setStatus(Constants.STATUS_EFFECT);
        controlInfo.setInputUser(Constants.ADMIN);
        controlInfo.setUpdateUser(Constants.ADMIN);
        try {
            this.controlInfoService.save(controlInfo);
            resultDto.setCode(200);
            resultDto.setData(controlInfo);
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
     * @return com.bztc.dto.ResultDto<com.bztc.entity.controlInfo>
     * @author daism
     * @date 2022-10-14 09:55:17
     */
    @PostMapping("/update")
    public ResultDto<ControlInfo> update(@RequestBody ControlInfo controlInfo) {
        log.info("更新入参：{}", JSONUtil.toJsonStr(controlInfo));
        ResultDto<ControlInfo> resultDto = new ResultDto<>();
        controlInfo.setUpdateUser(Constants.ADMIN);
        try {
            this.controlInfoService.updateById(controlInfo);
            resultDto.setCode(200);
            resultDto.setData(controlInfo);
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
    public ResultDto<Integer> delete(@RequestBody ControlInfo controlInfo) {
        log.info("删除入参：{}", JSONUtil.toJsonStr(controlInfo));
        ResultDto<Integer> resultDto = new ResultDto<>();
        if (Constants.STATUS_EFFECT.equals(controlInfo.getStatus())) {
            controlInfo.setUpdateUser(Constants.ADMIN);
            controlInfo.setStatus(Constants.STATUS_NOAVAIL);
            UpdateWrapper<ControlInfo> wrapper = new UpdateWrapper<>();
            wrapper.eq("control_id", controlInfo.getControlId());
            wrapper.set("status", Constants.STATUS_NOAVAIL);
            this.controlInfoService.update(wrapper);
        } else {
            this.controlInfoService.removeById(controlInfo);
            authResContrService.deleteByControlId(controlInfo.getControlId());
        }
        resultDto.setCode(200);
        return resultDto;
    }


}
