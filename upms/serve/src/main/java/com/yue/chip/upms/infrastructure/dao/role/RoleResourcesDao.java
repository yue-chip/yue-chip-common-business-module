package com.yue.chip.upms.infrastructure.dao.role;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.po.role.RoleResourcesPo;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:32
 * @description RoleResurcesDao
 */
public interface RoleResourcesDao extends BaseDao<RoleResourcesPo> {

    /**
     * 根据角色id删除绑定的资源
     * @param roleId
     * @return
     */
    public int deleteByRoleId(Long roleId);

    /**
     * 根据资源id删除绑定的资源
     * @param resourcesId
     * @return
     */
    public int deleteByResourcesId(Long resourcesId);
}
