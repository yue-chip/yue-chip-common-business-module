package com.yue.chip.upms.application.service;

import com.yue.chip.upms.interfaces.dto.organizational.OrganizationalAddDto;
import com.yue.chip.upms.interfaces.dto.organizational.OrganizationalUpdateDto;
import com.yue.chip.upms.interfaces.dto.role.RoleResourcesAddDto;
import com.yue.chip.upms.interfaces.dto.user.UserAddOrUpdateDto;
import com.yue.chip.upms.interfaces.dto.user.UserRoleAddDto;
import com.yue.chip.upms.interfaces.dto.user.UserUpdatePasswordDto;
import com.yue.chip.upms.interfaces.vo.user.UserVo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/3/4 上午11:33
 */
public interface UpmsApplication {

    /**
     * 角色绑定资源权限
     * @param roleResourcesAddDto
     */
    public void roleBindResources(@NotNull RoleResourcesAddDto roleResourcesAddDto);

    /**
     * 用户绑定角色
     * @param userRoleAddDto
     */
    public void userBindRole(@NotNull UserRoleAddDto userRoleAddDto);

    /**
     * 删除角色
     * @param roleId
     */
    public void deleteRole(@NotNull Long roleId);

    /**
     * 根据id删除资源
     * @param resourcesId
     */
    public void deleteResources(@NotNull Long resourcesId);

    /**
     * 保存用户
     * @param userAddOrUpdateDto
     */
    public void saveUser(@NotNull UserAddOrUpdateDto userAddOrUpdateDto);

    /**
     * 保存用户
     * @param userAddOrUpdateDto
     */
    public void saveUser1(@NotNull UserAddOrUpdateDto userAddOrUpdateDto);

    /**
     * 修改用户
     * @param userAddOrUpdateDto
     */
    public void updateUser(@NotNull UserAddOrUpdateDto userAddOrUpdateDto);

    /**
     * 修改用户密码
     * @param userUpdatePasswordDto
     */
    public void updateUserPassword(@NotNull UserUpdatePasswordDto userUpdatePasswordDto);

    /**
     * 删除用户
     * @param ids
     */
    public void deleteUser(@NotNull @Size(min = 1) List<Long> ids);

    /**
     * 添加组织架构
     * @param organizationalAddDto
     */
    public void saveOrganizational(@NotNull OrganizationalAddDto organizationalAddDto);

    /**
     * 需改组织机构
     * @param organizationalUpdateDto
     */
    public void updateOrganizational(@NotNull OrganizationalUpdateDto organizationalUpdateDto);

    /**
     * 删除组织机构
     * @param ids
     */
    public void deleteOrganizational(@NotNull @Size(min = 1) List<Long> ids);

    public UserVo test(String name);

    @Cacheable(
            value = {"test"},
            key = "#id + '-' +#name"
    )
    public String testCache(Long id,String name);

    @Cacheable(
            value = {"urlSingle"},
            key = "#tableId + '-' + #fileFieldName + '-' +#tableName+ '-' +#tenantNumber"
    )
    String getUrlSingle(@NotNull Long tableId, @NotBlank String fileFieldName, @NotBlank String tableName, @NotNull Long tenantNumber);

}
