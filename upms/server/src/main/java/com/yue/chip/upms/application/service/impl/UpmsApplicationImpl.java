package com.yue.chip.upms.application.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.yue.chip.common.business.expose.file.FileExposeService;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.exception.BusinessException;
import com.yue.chip.test.TestExpose;
import com.yue.chip.upms.application.service.UpmsApplication;
import com.yue.chip.upms.assembler.organizational.OrganizationalMapper;
import com.yue.chip.upms.definition.user.UserDefinition;
import com.yue.chip.upms.domain.aggregates.Organizational;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.domain.aggregates.Role;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.organizational.OrganizationalRepository;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.domain.service.upms.UpmsDomainService;
import com.yue.chip.upms.assembler.user.UserMapper;
import com.yue.chip.upms.infrastructure.dao.organizational.OrganizationalUserDao;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.upms.interfaces.dto.organizational.OrganizationalAddDto;
import com.yue.chip.upms.interfaces.dto.organizational.OrganizationalUpdateDto;
import com.yue.chip.upms.interfaces.dto.role.RoleResourcesAddDto;
import com.yue.chip.upms.interfaces.dto.user.UserAddOrUpdateDto;
import com.yue.chip.upms.interfaces.dto.user.UserRoleAddDto;
import com.yue.chip.upms.interfaces.dto.user.UserUpdatePasswordDto;
import com.yue.chip.upms.interfaces.vo.user.UserVo;
import com.yue.chip.utils.CurrentUserUtil;
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

import java.util.Arrays;
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

    @Resource
    private OrganizationalRepository organizationalRepository;

    @DubboReference()
    private TestExpose testExpose;

    @Resource
    private UserMapper userMapper;

    @Resource
    private OrganizationalMapper organizationalMapper;

    @DubboReference
    private FileExposeService fileExposeService;

    @Resource
    private PasswordEncoder passwordEncoder;

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
//    @GlobalTransactional(rollbackFor = {Exception.class})
    @Transactional(rollbackFor = {Exception.class})
    public void saveUser(@NotNull UserAddOrUpdateDto userAddOrUpdateDto) {
        //检查用户是否存在
        User user = User.builder().username(userAddOrUpdateDto.getUsername()).build();
        Assert.isFalse(user.checkUsernameIsExist(), () -> {return new BusinessException("该帐号已存在");});
        //保存用户
        User newUser = upmsRepository.saveUser(userMapper.toUserPo(userAddOrUpdateDto));
        //保存用户与组织架构的关联关系
        upmsDomainService.userOrganizational(newUser.getId(),userAddOrUpdateDto.getOrganizationalId());
        //保存头像
        fileExposeService.save(newUser.getId(), UserPo.TABLE_NAME,UserDefinition.PROFILE_PHOTO_FIELD_NAME, Arrays.asList(userAddOrUpdateDto.getProfilePhotoId()) );
    }

    @Override
//    @GlobalTransactional(rollbackFor = {Exception.class})
    @Transactional(rollbackFor = {Exception.class})
    public void updateUser(UserAddOrUpdateDto userAddOrUpdateDto) {
        //更新头像
        if (Objects.nonNull(userAddOrUpdateDto.getProfilePhotoId())) {
            fileExposeService.save(userAddOrUpdateDto.getId(),UserPo.TABLE_NAME,UserPo.PROFILE_PHOTO_FIELD_NAME,userAddOrUpdateDto.getProfilePhotoId());
        }
        //修改用户
        upmsRepository.updateUser(userMapper.toUserPo(userAddOrUpdateDto));
        //保存用户与组织架构的关联关系
        upmsDomainService.userOrganizational(userAddOrUpdateDto.getId(),userAddOrUpdateDto.getOrganizationalId());
        //保存头像
        fileExposeService.save(userAddOrUpdateDto.getId(), UserPo.TABLE_NAME,UserDefinition.PROFILE_PHOTO_FIELD_NAME,Arrays.asList(userAddOrUpdateDto.getProfilePhotoId()));
    }

    @Override
    public void updateUserPassword(UserUpdatePasswordDto userUpdatePasswordDto) {
//        upmsRepository.updateUserPassword(CurrentUserUtil.getCurrentUserId(),passwordEncoder.encode(SecureUtil.md5(userUpdatePasswordDto.getPassword())));
        upmsRepository.updateUserPassword(Objects.isNull(userUpdatePasswordDto.getUserId())?CurrentUserUtil.getCurrentUserId():userUpdatePasswordDto.getUserId(),
                passwordEncoder.encode(userUpdatePasswordDto.getPassword()));
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
                //删除用户与组织机构的关联关系
                upmsDomainService.userOrganizational(id,null);
                //删除机构负责人
                organizationalRepository.deleteLeader(id);
                //删除网格管理员
                organizationalRepository.deleteGridByUserId(id);
            });
        }
    }

    @Override
    public void saveOrganizational(OrganizationalAddDto organizationalAddDto) {
        //检查结构名称是否存在
        Boolean nameIsExist = Organizational.builder()
                .parentId(organizationalAddDto.getParentId())
                .name(organizationalAddDto.getName())
                .build()
                .checkNameIsExist();
        if (nameIsExist) {
            BusinessException.throwException("该机构名称已经存在");
        }
        //设置默认状态
        organizationalAddDto.setState(State.NORMAL);
        //保存
        organizationalRepository.saveOrganizational(organizationalMapper.toOrganizationalPo(organizationalAddDto));
    }

    @Override
    public void updateOrganizational(OrganizationalUpdateDto organizationalUpdateDto) {
        //检查结构名称是否存在
        Boolean nameIsExist = Organizational.builder()
                .parentId(organizationalUpdateDto.getParentId())
                .name(organizationalUpdateDto.getName())
                .id(organizationalUpdateDto.getId())
                .build()
                .checkNameIsExist();
        if (nameIsExist) {
            BusinessException.throwException("该机构名称已经存在");
        }
        organizationalRepository.updateOrganizational(organizationalMapper.toOrganizationalPo(organizationalUpdateDto));
    }

    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public void deleteOrganizational(List<Long> ids) {
        ids.forEach(id ->{
            deleteOrganizational(id);
            //删除所有的子部门
            List<Organizational> listChildren = organizationalRepository.findAllChildren(id);
            listChildren.forEach(organizational -> {
                deleteOrganizational(organizational.getId());
            });
        });
    }

    private void deleteOrganizational(Long id) {
        //删除用户组织机构的关联关系
        organizationalRepository.deleteOrganizationalUserByOrganizationalId(id);
        //删除组织机构
        organizationalRepository.deleteOrganizationalById(id);
        //删除机构下的网格
        organizationalRepository.deleteGridByOrganizationalId(id);
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
//        UserDefinition userDefinition = testExpose.test1("");
        return UserVo.builder().name("张三").build();
    }


}