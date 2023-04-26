package com.yue.chip.upms.infrastructure.repository.upms.impl;

import com.yue.chip.core.IPageResultData;
import com.yue.chip.core.PageResultData;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.domain.aggregates.Role;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.infrastructure.assembler.resources.ResourcesMapper;
import com.yue.chip.upms.infrastructure.assembler.role.RoleMapper;
import com.yue.chip.upms.infrastructure.assembler.user.UserMapper;
import com.yue.chip.upms.infrastructure.dao.resources.ResourcesDao;
import com.yue.chip.upms.infrastructure.dao.role.RoleDao;
import com.yue.chip.upms.infrastructure.dao.role.RoleResourcesDao;
import com.yue.chip.upms.infrastructure.dao.user.UserDao;
import com.yue.chip.upms.infrastructure.dao.user.UserRoleDao;
import com.yue.chip.upms.infrastructure.po.resources.ResourcesPo;
import com.yue.chip.upms.infrastructure.po.role.RolePo;
import com.yue.chip.upms.infrastructure.po.role.RoleResourcesPo;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.upms.infrastructure.po.user.UserRolePo;
import com.yue.chip.upms.interfaces.dto.resources.ResourcesAddDto;
import com.yue.chip.upms.interfaces.dto.resources.ResourcesUpdateDto;
import com.yue.chip.upms.interfaces.dto.role.RoleAddDto;
import com.yue.chip.upms.interfaces.dto.role.RoleUpdateDto;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTreeVo;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTreeListVo;
import com.yue.chip.upms.interfaces.vo.role.RoleVo;
import com.yue.chip.upms.interfaces.vo.user.UserVo;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author Mr.Liu
 * @date 2023/3/6 上午11:34
 */
@Repository
public class UpmsRepositoryImpl implements UpmsRepository {

    @Resource
    private UserDao userDao;
    @Resource
    private UserRoleDao userRoleDao;
    @Resource
    private RoleResourcesDao roleResourcesDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private ResourcesDao resourcesDao;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private ResourcesMapper resourcesMapper;

    @Override
    public Optional<User> findUserByUsername(String username) {
        Optional<UserPo> optional = userDao.find(username);
        if (optional.isPresent()) {
            User user = userMapper.toUser(optional.get());
            return Optional.ofNullable(user);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserById(Long id) {
        Optional<UserPo> optional = userDao.findFirstById(id);
        if (optional.isPresent()){
            return Optional.ofNullable(userMapper.toUser(optional.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<User> findUserByRoleId(Long roleId) {
        List<UserPo> list = userDao.findByRoleId(roleId);
        return userMapper.toUserList(list);
    }

    @Override
    public int deleteUserRole(Long roleId) {
        return userRoleDao.deleteByRoleId(roleId);
    }

    @Override
    public int deleteUserRoleByUserId(Long userId) {
        return userRoleDao.deleteByUserId(userId);
    }

    @Override
    public void deleteRole(Long roleId) {
        roleDao.deleteById(roleId);
    }

    @Override
    public void saveUserRole(Long roleId, Long[] userIds) {
        List<UserRolePo> list = new ArrayList<>();
        if (Objects.nonNull(userIds)) {
            for (Long id : userIds) {
                UserRolePo userRolePo = UserRolePo.builder()
                        .userId(id)
                        .roleId(roleId)
                        .build();
                list.add(userRolePo);
//                userRoleDao.save(userRolePo);
            }
            userRoleDao.saveAll(list);
        }
    }

    @Override
    public IPageResultData<List<RoleVo>> roleList(String name, String code, YueChipPage pageable) {
        Page<RolePo> page = roleDao.list(name,code, pageable);
        return (IPageResultData<List<RoleVo>>) PageResultData.convert(page,roleMapper.toRoleListVo(page.getContent()));
    }

    @Override
    public Optional<Role> findRoleByName(String name) {
        Optional<RolePo> optional = roleDao.findFirstByName(name);
        if (optional.isPresent()) {
            return Optional.ofNullable(roleMapper.toRole(optional.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<Role> findRoleByUserId(Long userId) {
        List<RolePo> list = roleDao.list(userId);
        if (list.size()>0){
            return roleMapper.toRoleList(list);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Optional<Role> findRoleById(Long id) {
        Optional<RolePo> optional = roleDao.findById(id);
        if (optional.isPresent()) {
            return Optional.ofNullable(roleMapper.toRole(optional.get()));
        }
        return Optional.empty();
    }

    @Override
    public void saveRole(RoleAddDto role) {
        roleDao.save(roleMapper.toRolePo(role));
    }

    @Override
    public void updateRole(RoleUpdateDto role) {
        roleDao.update(roleMapper.toRolePo(role));
    }

    @Override
    public List<ResourcesTreeListVo> findResourcesToTreeList(Long userId, Long parentId, Scope scope) {
        List<ResourcesPo> list ;
        if (Objects.nonNull(userId)) {
            list = resourcesDao.find(userId,parentId,scope);
        }else {
            list = resourcesDao.findByParentIdAndScopeOrderBySort(parentId,scope);
        }
        List<ResourcesTreeListVo> treeList;
        if (Objects.nonNull(list) && list.size()>0){
            treeList = new ArrayList<>();
        } else {
            treeList = null;
        }
        list.forEach(resourcesPo -> {
            ResourcesTreeListVo resourcesTree = resourcesMapper.toResourcesTreeListVo(resourcesPo);
            resourcesTree.setChildren(findResourcesToTreeList(userId,resourcesPo.getId(),scope));
            treeList.add(resourcesTree);
        });
        return treeList;
    }

    @Override
    public List<ResourcesTreeListVo> findResourcesToTreeList(Long parentId, Scope scope) {
        return findResourcesToTreeList(null,parentId,scope);
    }

    @Override
    public List<ResourcesTreeVo> findResourcesToTree(Long parentId, Scope scope) {
        List<ResourcesTreeListVo> list = findResourcesToTreeList(parentId,scope);
        return resourcesMapper.toResourcesTreeVo(list);
    }

    @Override
    public Optional<Resources> findResourcesByCode(String code) {
        return convertResources(resourcesDao.findFirstByCode(code));
    }

    @Override
    public Optional<Resources> findResourcesById(Long id) {
        return convertResources(resourcesDao.findById(id));
    }

    @Override
    public List<Resources> findResourcesByParentId(Long parentId) {
        return resourcesMapper.toResourcesList(resourcesDao.findByParentId(parentId));
    }

    @Override
    public List<Resources> findResourcesByRoleId(Long roleId) {
        List<ResourcesPo> resourcesPoList = resourcesDao.find(roleId);
        return resourcesMapper.toResourcesList(resourcesPoList);
    }

    @Override
    public Optional<Resources> findResourcesByName(String name) {
        return convertResources(resourcesDao.findFirstByName(name));
    }

    @Override
    public Optional<Resources> findResourcesByNameAndParentId(String name, Long parentId) {
        return convertResources(resourcesDao.findFirstByNameAndParentId(name,parentId));
    }

    @Override
    public Optional<Resources> findResourcesByUrl(String url) {
        return convertResources(resourcesDao.findFirstByUrl(url));
    }

    @Override
    public Resources saveResources(ResourcesAddDto resources) {
        ResourcesPo resourcesPo = resourcesMapper.toResourcesPo(resources);
        resourcesPo = resourcesDao.save(resourcesPo);
        return resourcesMapper.toResources(resourcesPo);
    }

    @Override
    public void saveAllRoleResources(List<RoleResourcesPo> list) {
        roleResourcesDao.saveAll(list);
    }

    @Override
    public void updateResources(ResourcesUpdateDto resources) {
        ResourcesPo resourcesPo = resourcesMapper.toResourcesPo(resources);
        resourcesDao.update(resourcesPo);
    }

    @Override
    public void deleteResources(Long id) {
        resourcesDao.deleteById(id);
    }

    @Override
    public void deleteRoleResourcesByRoleId(Long roleId) {
        if (Objects.nonNull(roleId)) {
            roleResourcesDao.deleteByRoleId(roleId);
        }
    }

    @Override
    public void deleteRoleResourcesByResourcesId(Long resourcesId) {
        if (Objects.nonNull(resourcesId)) {
            roleResourcesDao.deleteByResourcesId(resourcesId);
        }
    }

    @Override
    public IPageResultData<List<UserVo>> userList(String name, Pageable pageable) {
        Page<UserPo> page = userDao.find(name,null,pageable);
        return (IPageResultData<List<UserVo>>) PageResultData.convert(page,userMapper.toUserListVo(page.getContent()));
    }

    @Override
    public User saveUser(UserPo userPo) {
        userPo = userDao.save(userPo);
        return userMapper.toUser(userPo);
    }

    @Override
    public void updateUser(UserPo userPo) {
        userDao.update(userPo);
    }

    @Override
    public void deleteUser(Long id) {
        userDao.deleteById(id);
    }

    private Optional<Resources> convertResources(Optional<ResourcesPo> optional) {
        if (optional.isPresent()) {
            return Optional.ofNullable(resourcesMapper.toResources(optional.get()));
        }
        return Optional.empty();
    }
}
