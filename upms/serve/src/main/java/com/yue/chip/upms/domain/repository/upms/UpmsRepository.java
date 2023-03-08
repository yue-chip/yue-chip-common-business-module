package com.yue.chip.upms.domain.repository.upms;

import com.yue.chip.core.IPageResultData;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.domain.aggregates.Role;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.interfaces.dto.resources.ResourcesAddDto;
import com.yue.chip.upms.interfaces.dto.resources.ResourcesUpdateDto;
import com.yue.chip.upms.interfaces.dto.role.RoleAddDto;
import com.yue.chip.upms.interfaces.dto.role.RoleUpdateDto;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTree;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTreeList;
import com.yue.chip.upms.interfaces.vo.role.RoleListVo;
import com.yue.chip.upms.interfaces.vo.user.UserListVo;
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
    public Optional<User> findUserByName(String username);

    /**
     * 根据用户id查询用户
     *
     * @param id
     * @return
     */
    public Optional<User> findUserById(Long id);

    /**
     * 根据角色查询关联的用户
     * @param roleId
     * @return
     */
    public List<User> findUserByRoleId(Long roleId);

    /**
     * 角色列表
     *
     * @param name
     * @param code
     * @param pageable
     * @return
     */
    public IPageResultData<List<RoleListVo>> roleList(String name, String code, YueChipPage pageable);

    /**
     * 根据名称查询角色
     * @param name
     * @return
     */
    public Optional<Role> findRoleByName(String name);

    /**
     * 根据用户查询关联的角色
     * @param userId
     * @return
     */
    public List<Role> findRoleByUserId(Long userId);

    /**
     * 根据角色id查询角色
     *
     * @param id
     * @return
     */
    public Optional<Role> findRoleById(Long id);

    /**
     * 新增
     * @param role
     * @return
     */
    public void saveRole(RoleAddDto role);

    /**
     * 修改
     * @param role
     */
    public void updateRole(RoleUpdateDto role);

    /**
     * 根据用户id查询树形结构权限
     *
     * @param userId
     * @param parentId
     * @param scope
     * @return
     */
    public List<ResourcesTreeList> findResourcesToTreeList(Long userId, Long parentId, Scope scope);

    /**
     * 查询树形结构数据
     * @param parentId
     * @param scope
     * @return
     */
    public List<ResourcesTreeList> findResourcesToTreeList(Long parentId, Scope scope);

    public List<ResourcesTree> findResourcesToTree(Long parentId, Scope scope);

    /**
     * 根据编码查询
     * @param code
     * @return
     */
    public Optional<Resources> findResourcesByCode(String code);

    /**
     * 根据角色查询关联的资源
     * @param roleId
     * @return
     */
    public List<Resources> findResourcesByRoleId(Long roleId);

    /**
     * 根据名称查询资源
     * @param name
     * @return
     */
    public Optional<Resources> findResourcesByName(String name);

    /**
     * 根据名称查询当前节点下的资源
     * @param name
     * @param parentId
     * @return
     */
    public Optional<Resources> findResourcesByNameAndParentId(String name,Long parentId);

    /**
     * 根据url查询资源
     * @param url
     * @return
     */
    public Optional<Resources> findResourcesByUrl(String url);

    /**
     * 新建资源
     *
     * @param resources
     * @return
     */
    public Resources saveResources(ResourcesAddDto resources);

    /**
     * 修改资源
     * @param resources
     */
    public void  updateResources(ResourcesUpdateDto resources);

    /**
     * 根据角色id删除 绑定的资源权限
     * @param roleId
     */
    public void deleteRoleResources(Long roleId);

    /**
     * 绑定角色资源
     * @param roleId
     * @param resourcesIds
     */
    public void saveRoleResources(Long roleId,Long[] resourcesIds);

    /**
     * 用户列表
     *
     * @param name
     * @return
     */
    public IPageResultData<List<UserListVo>> userList(String name, Pageable pageable);

}
