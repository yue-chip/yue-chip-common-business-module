package com.yue.chip.upms.infrastructure.dao.social.impl;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.dao.social.UserSocialDaoEx;
import com.yue.chip.upms.infrastructure.po.user.UserSocialPo;
import jakarta.annotation.Resource;

public class UserSocialDaoImpl implements UserSocialDaoEx {

    @Resource
    BaseDao<UserSocialPo> baseDao;

}
