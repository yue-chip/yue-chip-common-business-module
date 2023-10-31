package com.yue.chip.upms.domain.repository.organizational;

import com.yue.chip.upms.vo.UserExposeVo;

import java.util.List;
import java.util.Set;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2023-10-31
 */
public interface UserRepository {
    List<UserExposeVo> findAllByIdIn(Set<Long> userIds);
}
