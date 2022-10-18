package com.bztc.web.rest;

import com.bztc.dto.ResultDto;
import com.bztc.dto.SessionInfoDto;
import com.bztc.service.AuthResContrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author daism
 * @create 2022-10-18 18:32
 * @description session信息
 */
@RestController
@RequestMapping("/api/sessionResource")
@Slf4j
public class SessionResource {
    @Autowired
    private AuthResContrService authResContrService;
    /*
     * 描述：获取session信息
     * @author daism
     * @date 2022-10-18 18:35:19
     * @param userName
     * @return com.bztc.dto.ResultDto<com.bztc.dto.SessionInfoDto>
     */
    @GetMapping("/getsession")
    public ResultDto<SessionInfoDto> getSession(@RequestParam("userName") String userName){
        log.info("获取session信息入参：{}",userName);
        return authResContrService.getSession(userName);
    }
}
