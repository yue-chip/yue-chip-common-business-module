package com.yue.chip.upms.infrastructure.dao.user;

import com.yue.chip.upms.infrastructure.po.user.UserPo;

import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:33
 * @description UserDaoEx
 */
public interface UserDaoEx {

    public Optional<UserPo> find(String username);
}
