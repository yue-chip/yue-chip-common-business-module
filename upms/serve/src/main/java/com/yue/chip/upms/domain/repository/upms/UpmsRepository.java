package com.yue.chip.upms.domain.repository.upms;

import com.yue.chip.core.IPageResultData;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.domain.aggregates.Role;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.aggregates.UserWeixin;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.infrastructure.po.resources.ResourcesPo;
import com.yue.chip.upms.infrastructure.po.role.RolePo;
import com.yue.chip.upms.infrastructure.po.role.RoleResourcesPo;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTreeVo;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTreeListVo;
import com.yue.chip.upms.interfaces.vo.role.RoleVo;
import com.yue.chip.upms.interfaces.vo.user.UserVo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/3/6 上午11:34
 */
public interface UpmsRepository {

    /**
     * 根据登录账号获取用户
     * @param username
     * @return
     */
    public Optional<User> findUserByUsername(@NotBlank String username);

    /**
     * 根据登录帐号查询微信登录用户
     * @param username
     * @return
     */
    public Optional<UserWeixin> findUserWeixinByUsername(@NotBlank String username);

    /**
     * 根据用户id查询用户
     *
     * @param id
     * @return
     */
    public Optional<User> findUserById(@NotNull Long id);

    /**
     * 根据角色查询关联的用户
     * @param roleId
     * @return
     */
    public List<User> findUserByRoleId(@NotNull Long roleId);

    /**
     * 根据角色删除用户与角色的绑定关系
     * @param roleId
     * @return
     */
    public int deleteUserRole(@NotNull Long roleId);

    /**
     * 根据用户删除用户与角色的绑定关系
     * @param userId
     * @return
     */
    public int deleteUserRoleByUserId(@NotNull Long userId);

    /**
     * 根据id删除角色
     *
     * @param roleId
     */
    public void deleteRole(@NotNull Long roleId);

    /**
     * 关联用户与角色关系
     * @param roleId
     * @param userIds
     */
    public void saveUserRole(@NotNull Long roleId,@NotNull @Size(min = 1) Long[] userIds);

    /**
     * 角色列表
     *
     * @param name
     * @param code
     * @param pageable
     * @return
     */
    public IPageResultData<List<RoleVo>> roleList(String name, String code,@NotNull YueChipPage pageable);

    /**
     * 根据名称查询角色
     * @param name
     * @return
     */
    public Optional<Role> findRoleByName(@NotBlank String name);

    /**
     * 根据用户查询关联的角色
     * @param userId
     * @return
     */
    public List<Role> findRoleByUserId(@NotNull Long userId);

    /**
     * 根据角色id查询角色
     *
     * @param id
     * @return
     */
    public Optional<Role> findRoleById(@NotNull Long id);

    /**
     * 新增
     * @param role
     * @return
     */
    public void saveRole(@NotNull RolePo role);

    /**
     * 修改
     * @param role
     */
    public void updateRole(@NotNull RolePo role);

    /**
     * 根据用户id查询树形结构权限
     *
     * @param userId
     * @param parentId
     * @param scope
     * @return
     */
    public List<ResourcesTreeListVo> findResourcesToTreeList(Long userId,@NotNull Long parentId,@NotNull Scope scope);

    /**
     * 查询树形结构数据
     * @param parentId
     * @param scope
     * @return
     */
    public List<ResourcesTreeListVo> findResourcesToTreeList(@NotNull Long parentId,@NotNull Scope scope);

    /**
     * 查询树形结构数据
     * @param parentId
     * @param scope
     * @return
     */
    public List<ResourcesTreeVo> findResourcesToTree(@NotNull Long parentId, @NotNull Scope scope);

    /**
     * 根据编码查询
     * @param code
     * @return
     */
    public Optional<Resources> findResourcesByCode(@NotBlank String code);

    /**
     * 根据资源id查询资源
     * @param id
     * @return
     */
    public Optional<Resources> findResourcesById(@NotNull Long id);

    /**
     * 根据父节点id查子节点
     * @param parentId
     * @return
     */
    public List<Resources> findResourcesByParentId(@NotNull Long parentId);

    /**
     * 根据角色查询关联的资源
     *
     * @param roleId
     * @return
     */
    public List<Resources> findResourcesByRoleId(@NotNull Long roleId);

    /**
     * 根据名称查询资源
     * @param name
     * @return
     */
    public Optional<Resources> findResourcesByName(@NotBlank String name);

    /**
     * 根据名称查询当前节点下的资源
     * @param name
     * @param parentId
     * @return
     */
    public Optional<Resources> findResourcesByNameAndParentId(@NotBlank String name,@NotNull Long parentId);

    /**
     * 根据url查询资源
     * @param url
     * @return
     */
    public Optional<Resources> findResourcesByUrl(@NotBlank String url);

    /**
     * 新建资源
     *
     * @param resources
     * @return
     */
    public Resources saveResources(@NotNull ResourcesPo resources);

    /**
     * 批量保存角色关联资源
     * @param list
     */
    public void saveAllRoleResources(@NotNull @Size(min = 1) List<RoleResourcesPo> list);

    /**
     * 修改资源
     *
     * @param resourcesPo
     */
    public void  updateResources(@NotNull ResourcesPo resourcesPo);

    /**
     * 根据id删除资源
     * @param id
     */
    public void deleteResources(@NotNull Long id);

    /**
     * 根据角色id删除 绑定的资源权限
     * @param roleId
     */
    public void deleteRoleResourcesByRoleId(@NotNull Long roleId);

    /**
     * 根据资源id删除 绑定的资源权限
     * @param resourcesId
     */
    public void deleteRoleResourcesByResourcesId(@NotNull Long resourcesId);

    /**
     * 用户列表
     *
     * @param name
     * @return
     */
    public IPageResultData<List<UserVo>> userList(String name, @NotNull Pageable pageable);

    /**
     * 保存用户
     * @param userPo
     * @return
     */
    public User saveUser(@NotNull UserPo userPo);

    /**
     * 修改用户
     * @param userPo
     */
    public void updateUser(@NotNull UserPo userPo);

    /**
     * 根据id删除用户
     *
     * @param id
     */
    public void deleteUser(@NotNull Long id);



}
