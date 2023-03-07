package com.yue.chip.upms.application.service;

import com.yue.chip.upms.interfaces.dto.role.RoleResourcesAddDto;

/**
 * @author Mr.Liu
 * @date 2023/3/4 上午11:33
 */
public interface UpmsApplication {

    /**
     * 角色绑定资源权限
     * @param roleResourcesAddDto
     */
    public void roleBindResources(RoleResourcesAddDto roleResourcesAddDto);
}
