package com.yue.chip.upms.infrastructure.repository.upms.impl;

import com.yue.chip.core.IPageResultData;
import com.yue.chip.core.PageResultData;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.upms.assembler.resources.ResourcesMapper;
import com.yue.chip.upms.assembler.role.RoleMapper;
import com.yue.chip.upms.assembler.user.UserMapper;
import com.yue.chip.upms.assembler.weixin.UserWeiXinMapper;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.domain.aggregates.Role;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.aggregates.UserWeixin;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.infrastructure.dao.resources.ResourcesDao;
import com.yue.chip.upms.infrastructure.dao.role.RoleDao;
import com.yue.chip.upms.infrastructure.dao.role.RoleResourcesDao;
import com.yue.chip.upms.infrastructure.dao.user.UserDao;
import com.yue.chip.upms.infrastructure.dao.user.UserRoleDao;
import com.yue.chip.upms.infrastructure.dao.weixin.UserWeiXinDao;
import com.yue.chip.upms.infrastructure.po.resources.ResourcesPo;
import com.yue.chip.upms.infrastructure.po.role.RolePo;
import com.yue.chip.upms.infrastructure.po.role.RoleResourcesPo;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.upms.infrastructure.po.user.UserRolePo;
import com.yue.chip.upms.infrastructure.po.user.UserWeiXinPo;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTreeListVo;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTreeVo;
import com.yue.chip.upms.interfaces.vo.role.RoleVo;
import com.yue.chip.upms.interfaces.vo.user.UserVo;
import com.yue.chip.utils.CurrentUserUtil;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Resource
    private UserWeiXinMapper userWeiXinMapper;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findUserByUsername(String username) {
        Optional<UserPo> optional = userDao.findFirstByUsername(username);
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
    public Optional<User> findByIdAndTenantNumber(Long id, Long tenantNumber) {
        Optional<UserPo> optional = userDao.findByIdAndTenantNumber(id,tenantNumber);
        if (optional.isPresent()) {
            return Optional.ofNullable(userMapper.toUser(optional.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByGridIdAndTenantNumber(Long id, Long tenantNumber) {
        Optional<UserPo> optional = userDao.findByGridIdAndTenantNumber(id,tenantNumber);
        if (optional.isPresent()) {
            return Optional.ofNullable(userMapper.toUser(optional.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<User> findUserByIds(List<Long> userIds) {
        List<UserPo> userPoList = userDao.findAllByIdIn(userIds);
        return userMapper.toUser(userPoList);
    }

    @Override
    public Optional<User> findUserByGridId(Long gridId) {
        List<UserPo> list = userDao.findUserByGridId(gridId);
        if (list.size()>0) {
            return Optional.ofNullable(userMapper.toUser(list.get(0)));
        }
        return Optional.empty();
    }

    @Override
    public List<User> findUserByOrganizationalId(List<Long> organizationalIds) {
        List<UserPo> userPoList = userDao.findUserByOrganizationalId(organizationalIds,State.NORMAL);
        List<User> list = userMapper.toUser(userPoList);
        return list;
    }

    @Override
    public List<User> findUserByOrganizationalId(Long organizationalId) {
        List<UserPo> list = userDao.findUserByOrganizationalId(organizationalId, State.NORMAL );
        return userMapper.toUser(list);
    }

    @Override
    public List<User> findUserByRoleId(Long roleId) {
        List<UserPo> list = userDao.findByRoleId(roleId);
        return userMapper.toUserList(list);
    }

    @Override
    public void updateUserPassword(Long userId, String password) {
        userDao.updatePassword(userId,password);
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
//                userRoleDao.saveTenant(userRolePo);
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
    public void saveRole(@NotNull RolePo role) {
        roleDao.save(role);
    }

    @Override
    public void updateRole(@NotNull RolePo role) {
        roleDao.update(role);
    }

    @Override
    public List<ResourcesTreeListVo> findResourcesToTreeList(Long userId, Long parentId, Scope scope) {
        List<ResourcesPo> list ;
        if (Objects.nonNull(userId)) {
            list = resourcesDao.find(userId,parentId,scope);
        }else {
            list = resourcesDao.findByParentIdAndScopeOrderBySortAsc(parentId,scope);
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
    public Resources saveResources(@NotNull ResourcesPo resourcesPo) {
        resourcesPo = resourcesDao.save(resourcesPo);
        return resourcesMapper.toResources(resourcesPo);
    }

    @Override
    public void saveAllRoleResources(List<RoleResourcesPo> list) {
        roleResourcesDao.saveAll(list);
    }

    @Override
    public void updateResources(@NotNull ResourcesPo resourcesPo) {
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
        List<User> listUser = userMapper.toUserList(page.getContent());
        return (IPageResultData<List<UserVo>>) PageResultData.convert(page,userMapper.toUserListVo(listUser));
    }

    @Override
    public IPageResultData<List<User>> userList(List<Long> ids, String name, Pageable pageable) {
        Page<UserPo> page = userDao.find(ids, name, pageable);
        List<User> listUser = userMapper.toUserList(page.getContent());
        return (IPageResultData<List<User>>) PageResultData.convert(page,userMapper.toUserListVo(listUser));
    }

    @Override
    public User saveUser(UserPo userPo) {
        userPo.setPassword(passwordEncoder.encode(userPo.getPassword()));
        userPo.setTenantNumber(CurrentUserUtil.getCurrentUserTenantNumber(true));
        userPo = userDao.save(userPo);
        return userMapper.toUser(userPo);
    }

    @Override
    //    @CachePut(value = User.CACHE_KEY,key = "#userPo.id")
    @CacheEvict(value = User.CACHE_KEY, key = "#userPo.id")
    public void updateUser(UserPo userPo) {
        userDao.update(userPo);
    }

    @Override
    @CacheEvict(value = User.CACHE_KEY, key = "#id")
    public void deleteUser(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public List<User> findAllByNameOrPhoneNumber(String name,String phoneNumber) {
        List<UserPo> allByNameOrPhoneNumber = userDao.findAllByNameLikeOrPhoneNumberLike(name,phoneNumber);
        List<User> userList = userMapper.toUserList(allByNameOrPhoneNumber);
        return userList;
    }

    private Optional<Resources> convertResources(Optional<ResourcesPo> optional) {
        if (optional.isPresent()) {
            return Optional.ofNullable(resourcesMapper.toResources(optional.get()));
        }
        return Optional.empty();
    }
}
