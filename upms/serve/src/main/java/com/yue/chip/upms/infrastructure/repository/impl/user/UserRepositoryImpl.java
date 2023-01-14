package com.yue.chip.upms.infrastructure.repository.impl.user;

import com.yue.chip.core.repository.impl.BaseRepositoryImpl;
import com.yue.chip.upms.domain.repository.user.UserRepository;
import com.yue.chip.upms.infrastructure.dao.user.UserDao;
import com.yue.chip.upms.infrastructure.dao.user.UserRoleDao;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午4:14
 * @description UserRepositoryImpl
 */
@Repository
public class UserRepositoryImpl extends BaseRepositoryImpl<UserPo> implements UserRepository {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;


}
