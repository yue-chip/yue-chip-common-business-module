package com.yue.chip.upms;

import com.yue.chip.upms.vo.UserExposeVo;

import java.util.List;
import java.util.Set;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2023-10-28
 */
public interface UpmsExposeService {

    /**
     * 根据ids查询所有用户
     * @param userIds
     * @return
     */
    List<UserExposeVo> findAllByIdIn(List<Long> userIds);

    /**
     * 根据机构id查询用户
     * @param organizationalIds
     * @return
     */
    List<UserExposeVo> findAllByOrganizationalId(List<Long> organizationalIds);

}
