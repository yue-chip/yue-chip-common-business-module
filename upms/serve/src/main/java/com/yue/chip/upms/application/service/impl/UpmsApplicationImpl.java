package com.yue.chip.upms.application.service.impl;

import com.yue.chip.upms.application.service.UpmsApplication;
import com.yue.chip.upms.domain.aggregates.Role;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.domain.service.upms.UpmsDomainService;
import com.yue.chip.upms.infrastructure.po.role.RoleResourcesPo;
import com.yue.chip.upms.interfaces.dto.role.RoleResourcesAddDto;
import com.yue.chip.upms.interfaces.dto.user.UserRoleAddDto;
import jakarta.annotation.Resource;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = {Throwable.class})
    @Trace
    @Tags({@Tag(key = "userBindRole",value = "arg[0]")})
    public void roleBindResources(RoleResourcesAddDto roleResourcesAddDto) {
        //先删除已经绑定的资源
        upmsRepository.deleteRoleResources(roleResourcesAddDto.getRoleId());
        //保存角色关联的资源权限
        upmsRepository.saveRoleResources(roleResourcesAddDto.getRoleId(),roleResourcesAddDto.getResourcesIds());
    }

    @Override
    @Transactional(rollbackFor = {Throwable.class})
    @Trace
    @Tags({@Tag(key = "userBindRole",value = "arg[0]")})
    public void userBindRole(UserRoleAddDto userRoleAddDto) {
        //先删除用户与角色的绑定关系
        upmsRepository.deleteUserRoleByRoleId(userRoleAddDto.getRoleId());
        //保存用户与角色关系
        upmsRepository.saveUserRole(userRoleAddDto.getRoleId(), userRoleAddDto.getUserIds());
    }
}
