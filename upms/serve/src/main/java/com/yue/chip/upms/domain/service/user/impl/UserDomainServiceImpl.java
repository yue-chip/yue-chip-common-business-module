package com.yue.chip.upms.domain.service.user.impl;

import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.user.UserRepository;
import com.yue.chip.upms.domain.service.user.UserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午4:12
 * @description UserServiceImpl
 */
@Service
public class UserDomainServiceImpl implements UserDomainService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return user;
    }
}
