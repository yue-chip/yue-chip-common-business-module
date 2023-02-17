package com.yue.chip.upms.application.service.user.impl;

import com.yue.chip.upms.application.service.user.UserService;
import com.yue.chip.upms.domain.service.user.UserDomainService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author Mr.Liu
 * @date 2023/2/16 下午4:07
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDomainService userDomainService;
}
