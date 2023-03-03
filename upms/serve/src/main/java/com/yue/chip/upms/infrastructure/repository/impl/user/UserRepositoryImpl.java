package com.yue.chip.upms.infrastructure.repository.impl.user;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.repository.impl.BaseRepositoryImpl;
import com.yue.chip.upms.definition.aggregates.ResourcesVODefinition;
import com.yue.chip.upms.definition.aggregates.RoleARVODefinition;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.domain.aggregates.Role;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.user.UserRepository;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.enums.Type;
import com.yue.chip.upms.infrastructure.assembler.resources.ResourcesMapper;
import com.yue.chip.upms.infrastructure.assembler.user.UserMapper;
import com.yue.chip.upms.infrastructure.dao.user.UserDao;
import com.yue.chip.upms.infrastructure.dao.user.UserRoleDao;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午4:14
 * @description UserRepositoryImpl
 */
@Repository
public class UserRepositoryImpl extends BaseRepositoryImpl<UserPo> implements UserRepository {

    @Resource
    private UserDao userDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ResourcesMapper resourcesMapper;

    @Override
    public Optional<User> find( String username) {
        Optional<UserPo> optional = userDao.find(username);
        return convertToUser(optional);
    }

    @Override
    public Optional<User> find( Long userId) {
        Optional<UserPo> optional = userDao.findFirstById(userId);
        return convertToUser(optional);
    }

    private Optional<User> convertToUser(Optional<UserPo> optional) {
        if (optional.isPresent()) {
            User user = userMapper.userPoToUser(optional.get());
            user.setRoles(findRole(user));
            return Optional.ofNullable(user);
        }
        return Optional.empty();
    }

    /**
     * 根据用户获取角色
     * @param user
     * @return
     */
    private List<RoleARVODefinition> findRole(User user) {
        List<RoleARVODefinition> list = new ArrayList<>();
        Role role = Role.builder()
                .code("test")
                .state(State.NORMAL)
                .name("测试")
                .build();
        role.setResources(findResources(role));
        list.add(role);
        return list;
    }

    private List<ResourcesVODefinition> findResources(Role role) {
        List<ResourcesVODefinition> list = new ArrayList<>();
        Resources resources = Resources.builder()
                .code("test")
                .scope(Scope.CONSOLE)
                .state(State.NORMAL)
                .type(Type.MENU)
                .name("测试")
                .build();
        list.add(resources);
        return list;
    }
}
