package com.yue.chip.upms.domain.repository.user;

import com.yue.chip.core.repository.BaseRepository;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午4:13
 * @description UserRepository
 */

public interface UserRepository extends BaseRepository<UserPo> {

    public Optional<User> find(@NotNull String username);
}
