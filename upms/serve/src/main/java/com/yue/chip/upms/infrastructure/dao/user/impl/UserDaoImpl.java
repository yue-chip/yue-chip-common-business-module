package com.yue.chip.upms.infrastructure.dao.user.impl;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.dao.user.UserDaoEx;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:34
 * @description UserDaoImpl
 */
public class UserDaoImpl implements UserDaoEx {

    @Autowired
    public BaseDao<UserPo> baseDao;
}
