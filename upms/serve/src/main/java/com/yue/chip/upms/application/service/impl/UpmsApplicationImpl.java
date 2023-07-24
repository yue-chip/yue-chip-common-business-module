package com.yue.chip.upms.application.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.yue.chip.common.business.expose.file.FileExposeService;
import com.yue.chip.exception.BusinessException;
import com.yue.chip.test.TestExpose;
import com.yue.chip.upms.application.service.UpmsApplication;
import com.yue.chip.upms.definition.user.UserDefinition;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.domain.aggregates.Role;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.domain.service.upms.UpmsDomainService;
import com.yue.chip.upms.assembler.user.UserMapper;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.upms.interfaces.dto.role.RoleResourcesAddDto;
import com.yue.chip.upms.interfaces.dto.user.UserAddOrUpdateDto;
import com.yue.chip.upms.interfaces.dto.user.UserRoleAddDto;
import com.yue.chip.upms.interfaces.vo.user.UserVo;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    @DubboReference()
    private TestExpose testExpose;

    @Resource
    private UserMapper userMapper;


    @DubboReference
    private FileExposeService fileExposeService;

    @Override
    @Transactional(rollbackFor = {Throwable.class})
    @Trace
    @Tags({@Tag(key = "userBindRole",value = "arg[0]")})
    public void roleBindResources(RoleResourcesAddDto roleResourcesAddDto) {
        //先删除已经绑定的资源
        upmsRepository.deleteRoleResourcesByRoleId(roleResourcesAddDto.getRoleId());
        //保存角色关联的资源权限
        upmsDomainService.roleResources(roleResourcesAddDto.getRoleId(),roleResourcesAddDto.getResourcesIds());
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
            Assert.isFalse(role.getIsDefault(),() -> {return new BusinessException("默认角色不能删除");});
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
            Assert.isFalse(resources.getIsDefault(),() -> {return new BusinessException("默认资源不能删除");});
            //删除资源与角色的关联关系
            upmsRepository.deleteRoleResourcesByResourcesId(resourcesId);
            //删除资源
            upmsRepository.deleteResources(resourcesId);
        }
    }

    @Override
    public void saveUser(@NotNull UserAddOrUpdateDto userAddOrUpdateDto) {
        //检查用户是否存在
        User user = User.builder().username(userAddOrUpdateDto.getUsername()).build();
        Assert.isFalse(user.checkUsernameIsExist(), () -> {return new BusinessException("该帐号已存在");});
        //保存用户
        User newUser = upmsRepository.saveUser(userMapper.toUserPo(userAddOrUpdateDto));
        //保存头像
        fileExposeService.save(newUser.getId(), UserPo.TABLE_NAME,UserDefinition.PROFILE_PHOTO_FIELD_NAME,userAddOrUpdateDto.getProfilePhotoId());
    }

    @Override
    public void updateUser(UserAddOrUpdateDto userAddOrUpdateDto) {
        //修改用户
        upmsRepository.updateUser(userMapper.toUserPo(userAddOrUpdateDto));
        //保存头像
        fileExposeService.save(userAddOrUpdateDto.getId(), UserPo.TABLE_NAME,UserDefinition.PROFILE_PHOTO_FIELD_NAME,userAddOrUpdateDto.getProfilePhotoId());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void deleteUser(List<Long> ids) {
        if (Objects.nonNull(ids)) {
            ids.forEach(id->{
                //删除用户与角色的关联
                upmsRepository.deleteUserRoleByUserId(id);
                //删除用户
                upmsRepository.deleteUser(id);
            });
        }
    }

    @Override
    @Trace
    @Tags({@Tag(key = "name",value = "arg[0]"),@Tag(key = "UserDefinition",value = "returnedObj")})
    @GlobalTransactional
    public UserVo test(String name) {
//        RoleAddDto roleAddDto = RoleAddDto.builder()
//                .name(UUID.randomUUID().toString())
//                .code(UUID.randomUUID().toString())
//                .build();
//        upmsRepository.saveRole(roleAddDto);
        UserDefinition userDefinition = testExpose.test1("");
        return UserVo.builder().name("张三").build();
    }


}