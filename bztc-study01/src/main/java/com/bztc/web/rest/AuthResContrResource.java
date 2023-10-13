package com.bztc.web.rest;

import cn.hutool.core.lang.Assert;
import com.bztc.dto.AuthSourceDto;
import com.bztc.dto.ResultDto;
import com.bztc.service.AuthResContrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author daism
 * @create 2022-10-17 15:35
 * @description 资源权限管理controller层
 */
@RestController
@RequestMapping("/api/authrescontrresource")
@Slf4j
public class AuthResContrResource {
    @Autowired
    private AuthResContrService authResContrService;

    /**
     * 根据roleid查询所关联的资源
     *
     * @param roleId
     * @return
     */
    @GetMapping("/queryauthrescontrbyroleid")
    public ResultDto<List<AuthSourceDto>> queryAuthResContrByRoleId(@RequestParam("roleId") String roleId) {
        return authResContrService.queryAuthResContrByRoleId(roleId);
    }

    /**
     * 查询所有资源
     *
     * @return
     */
    @GetMapping("/queryallauthrescontr")
    public ResultDto<List<AuthSourceDto>> queryAllAuthResContr() {
        return authResContrService.queryAllAuthResContr();
    }

    /**
     * 描述：保存角色关联权限
     *
     * @return int
     * @author daism
     * @date 2022-09-29 10:35:10
     */
    @PostMapping("/save")
    public ResultDto<Integer> save(@RequestBody List<AuthSourceDto> authSourceDtos, Integer roleId) {
        Assert.notNull(roleId, "roleId不能为空");
        return this.authResContrService.save(authSourceDtos, roleId);
    }
}
