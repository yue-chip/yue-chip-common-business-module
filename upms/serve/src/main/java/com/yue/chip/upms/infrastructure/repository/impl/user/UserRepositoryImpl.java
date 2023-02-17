package com.yue.chip.upms.infrastructure.repository.impl.user;

import com.yue.chip.core.repository.impl.BaseRepositoryImpl;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.user.UserRepository;
import com.yue.chip.upms.infrastructure.assembler.user.UserMapper;
import com.yue.chip.upms.infrastructure.dao.user.UserDao;
import com.yue.chip.upms.infrastructure.dao.user.UserRoleDao;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

    @Override
    public Optional<User> find(@NotNull String username) {
        Optional<UserPo> optional = userDao.find(username);
        return optional.isPresent()?Optional.ofNullable(userMapper.userPoToUser(optional.get())):Optional.empty();
    }
}
