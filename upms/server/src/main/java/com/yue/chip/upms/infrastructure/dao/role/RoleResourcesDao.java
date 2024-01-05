package com.yue.chip.upms.infrastructure.dao.role;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.po.role.RoleResourcesPo;
import javax.validation.constraints.NotNull;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:32
 * @description RoleResourcesDao
 */
public interface RoleResourcesDao extends BaseDao<RoleResourcesPo> {

    /**
     * 根据角色id删除绑定的资源
     * @param roleId
     * @return
     */
    public int deleteByRoleId(@NotNull Long roleId);

    /**
     * 根据资源id删除绑定的资源
     * @param resourcesId
     * @return
     */
    public int deleteByResourcesId(@NotNull Long resourcesId);
}
