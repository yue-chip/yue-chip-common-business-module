package com.yue.chip.upms.application.service.impl;

import com.yue.chip.upms.application.service.UpmsApplication;
import com.yue.chip.upms.domain.aggregates.Role;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.domain.service.upms.UpmsDomainService;
import com.yue.chip.upms.infrastructure.po.role.RoleResourcesPo;
import com.yue.chip.upms.interfaces.dto.role.RoleResourcesAddDto;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author Mr.Liu
 * @date 2023/3/6 上午11:28
 */
@Service
public class UpmsApplicationImpl implements UpmsApplication {

    @Resource
    private UpmsDomainService upmsDomainService;

    @Resource
    private UpmsRepository upmsRepository;

    @Override
    public void roleBindResources(RoleResourcesAddDto roleResourcesAddDto) {
        //先删除已经绑定的资源
        upmsRepository.deleteRoleResources(roleResourcesAddDto.getRoleId());
        //保存角色关联的资源权限
        upmsRepository.saveRoleResources(roleResourcesAddDto.getRoleId(),roleResourcesAddDto.getResourcesIds());
    }
}
