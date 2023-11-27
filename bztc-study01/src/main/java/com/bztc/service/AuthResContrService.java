package com.bztc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bztc.domain.AuthResContr;
import com.bztc.dto.AuthSourceDto;
import com.bztc.dto.ResultDto;
import com.bztc.dto.SessionInfoDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author daishuming
 * @description 针对表【auth_res_contr(权限关联表)】的数据库操作Service
 * @createDate 2022-10-18 09:25:40
 */
public interface AuthResContrService extends IService<AuthResContr> {
    /**
     * 描述：获取session
     *
     * @return com.bztc.dto.ResultDto<com.bztc.dto.SessionInfoDto>
     * @author daism
     * @date 2022-10-18 18:45:55
     */
    ResultDto<SessionInfoDto> getSession();

    /**
     * 根据roleid查询所关联的资源
     *
     * @param roleId
     * @return
     */
    ResultDto<List<AuthSourceDto>> queryAuthResContrByRoleId(@RequestParam("roleId") String roleId);

    /**
     * 查询所有资源
     *
     * @return
     */
    ResultDto<List<AuthSourceDto>> queryAllAuthResContr();

    /**
     * 保存用户关联权限
     *
     * @param authSourceDtos
     * @param roleId         roleid
     * @return
     */
    ResultDto<Integer> save(List<AuthSourceDto> authSourceDtos, Integer roleId);

    /**
     * 根据角色号删除权限
     *
     * @param roleId
     * @return
     */
    int deleteByRoleId(int roleId);

    /**
     * 根据控制点id删除
     *
     * @param controlId
     * @return
     */
    int deleteByControlId(int controlId);

    /**
     * 根据菜单id删除
     *
     * @param menuId
     * @return
     */
    int deleteByMenuId(int menuId);
}
