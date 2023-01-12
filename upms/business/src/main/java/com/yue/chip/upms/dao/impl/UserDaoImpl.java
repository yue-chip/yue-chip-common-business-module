package com.yue.chip.upms.dao.impl;

import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.dao.UserDaoEx;
import com.yue.chip.upms.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

public class UserDaoImpl implements UserDaoEx {

    @Autowired
    private BaseDao<User> baseDao;

    @Override
    public Page<User> test(YueChipPage yueChipPage) {
        return (Page<User>) baseDao.findNavigator(yueChipPage,"select u  from User u");
    }
}
