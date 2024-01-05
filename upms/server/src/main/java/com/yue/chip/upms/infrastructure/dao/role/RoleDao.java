package com.yue.chip.upms.infrastructure.dao.role;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.po.role.RolePo;
import javax.validation.constraints.NotBlank;

import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:31
 * @description RoleDao
 */
public interface RoleDao extends BaseDao<RolePo>, RoleDaoEx {

    /**
     * 根据名称查询角色
     * @param name
     * @return
     */
    Optional<RolePo> findFirstByName(@NotBlank String name);
}
