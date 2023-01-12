package com.yue.chip.upms.service.impl;

import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.service.BaseService;
import com.yue.chip.core.service.impl.BaseServiceImpl;
import com.yue.chip.upms.dao.UserDao;
import com.yue.chip.upms.entity.User;
import com.yue.chip.upms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Page<User> test(YueChipPage yueChipPage) {
        return userDao.test(yueChipPage);
    }
}
