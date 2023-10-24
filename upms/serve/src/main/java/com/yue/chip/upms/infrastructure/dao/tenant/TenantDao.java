package com.yue.chip.upms.infrastructure.dao.tenant;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.po.tenant.TenantPo;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/24 上午10:55
 */
public interface TenantDao extends BaseDao<TenantPo>,TenantDaoEx {

    /**
     * 根据名称查询租户
     * @param name
     * @return
     */
    public Optional<TenantPo> findFirstByName(@NotBlank String name);
}
