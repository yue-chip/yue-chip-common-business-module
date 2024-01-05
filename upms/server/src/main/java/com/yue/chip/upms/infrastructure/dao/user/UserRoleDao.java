package com.yue.chip.upms.infrastructure.dao.user;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.po.user.UserRolePo;
import javax.validation.constraints.NotNull;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:30
 * @description UserRoleDao
 */
public interface UserRoleDao extends BaseDao<UserRolePo> {

    /**
     * 根据角色删除用户与角色的绑定关系
     * @param roleId
     * @return
     */
    public int deleteByRoleId(@NotNull Long roleId);

    /**
     * 根据用户删除用户与角色的绑定关系
     * @param userId
     * @return
     */
    public int deleteByUserId(@NotNull Long userId);
}
