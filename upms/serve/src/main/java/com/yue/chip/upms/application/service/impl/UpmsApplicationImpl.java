package com.yue.chip.upms.application.service.impl;

import com.yue.chip.exception.BusinessException;
import com.yue.chip.test.TestExpose;
import com.yue.chip.upms.application.service.UpmsApplication;
import com.yue.chip.upms.definition.user.UserDefinition;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.domain.aggregates.Role;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.domain.service.upms.UpmsDomainService;
import com.yue.chip.upms.interfaces.dto.role.RoleAddDto;
import com.yue.chip.upms.interfaces.dto.role.RoleResourcesAddDto;
import com.yue.chip.upms.interfaces.dto.user.UserRoleAddDto;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

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

    @DubboReference
    private TestExpose testExpose;

    @Override
    @Transactional(rollbackFor = {Throwable.class})
    @Trace
    @Tags({@Tag(key = "userBindRole",value = "arg[0]")})
    public void roleBindResources(RoleResourcesAddDto roleResourcesAddDto) {
        //先删除已经绑定的资源
        upmsRepository.deleteRoleResourcesByRoleId(roleResourcesAddDto.getRoleId());
        //保存角色关联的资源权限
        upmsRepository.saveRoleResources(roleResourcesAddDto.getRoleId(),roleResourcesAddDto.getResourcesIds());
    }

    @Override
    @Transactional(rollbackFor = {Throwable.class})
    @Trace
    @Tags({@Tag(key = "userBindRole",value = "arg[0]")})
    public void userBindRole(UserRoleAddDto userRoleAddDto) {
        //先删除用户与角色的绑定关系
        upmsRepository.deleteUserRole(userRoleAddDto.getRoleId());
        //保存用户与角色关系
        upmsRepository.saveUserRole(userRoleAddDto.getRoleId(), userRoleAddDto.getUserIds());
    }

    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public void deleteRole(Long roleId) {
        //判断角色是否能删除
        Optional<Role> optional = upmsRepository.findRoleById(roleId);
        if (optional.isPresent()){
            Role role = optional.get();
            if (role.getIsDefault()) {
                BusinessException.throwException("默认角色不能删除");
            }
            //删除角色与用户的关联关系
            upmsRepository.deleteUserRole(roleId);
            //删除角色与资源的关联关系
            upmsRepository.deleteRoleResourcesByRoleId(roleId);
            //删除角色
            upmsRepository.deleteRole(roleId);
        }
    }

    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public void deleteResources(Long resourcesId) {
        Optional<Resources> optional = upmsRepository.findResourcesById(resourcesId);
        if (optional.isPresent()){
            Resources resources = optional.get();
            if (resources.getIsDefault()) {
                BusinessException.throwException("默认资源不能删除");
            }
            //删除资源与角色的关联关系
            upmsRepository.deleteRoleResourcesByResourcesId(resourcesId);
            //删除资源
            upmsRepository.deleteResources(resourcesId);
        }
    }

    @Override
    @Trace
    @Tags({@Tag(key = "name",value = "arg[0]"),@Tag(key = "UserDefinition",value = "returnedObj")})
    @GlobalTransactional
    public UserDefinition test(String name) {
        RoleAddDto roleAddDto = RoleAddDto.builder()
                .name(UUID.randomUUID().toString())
                .code(UUID.randomUUID().toString())
                .build();
        upmsRepository.saveRole(roleAddDto);
        testExpose.test("test code");
        return UserDefinition.builder().name("张三").build();
    }


}