package com.yue.chip.upms.application.service;

import com.yue.chip.upms.definition.user.UserDefinition;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.interfaces.dto.role.RoleResourcesAddDto;
import com.yue.chip.upms.interfaces.dto.user.UserRoleAddDto;
import com.yue.chip.upms.interfaces.vo.user.UserVo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.catalina.LifecycleState;

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
     * @param user
     */
    public void saveUser(@NotNull User user);

    /**
     * 删除用户
     * @param ids
     */
    public void deleteUser(@NotNull @Size(min = 1) List<Long> ids);

    public UserVo test(String name);

}
