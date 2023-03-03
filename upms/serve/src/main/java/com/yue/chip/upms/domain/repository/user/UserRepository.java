package com.yue.chip.upms.domain.repository.user;

import com.yue.chip.core.repository.BaseRepository;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.infrastructure.po.user.UserPo;

import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午4:13
 * @description UserRepository
 */

public interface UserRepository extends BaseRepository<UserPo> {

    /**
     * 根据登录账号获取用户
     * @param username
     * @return
     */
    public Optional<User> find(String username);

    /**
     * 根据用户id查询用户
     * @param userId
     * @return
     */
    public Optional<User> find(Long userId);


}
