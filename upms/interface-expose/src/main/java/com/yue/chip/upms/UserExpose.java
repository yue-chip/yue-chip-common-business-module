package com.yue.chip.upms;

import com.yue.chip.upms.vo.UserExposeVo;

import java.util.List;
import java.util.Set;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2023-10-31
 */
public interface UserExpose {

    /**
     * 根据ids查询所有用户
     * @param userIds
     * @return
     */
    List<UserExposeVo> findAllByIdIn(Set<Long> userIds);
}
