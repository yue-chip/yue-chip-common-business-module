package com.yue.chip.upms.infrastructure.dao.tenant;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.po.tenant.TenantPo;

import java.util.Optional;

public interface TenantDao extends BaseDao<TenantPo> ,TenantDaoEx{

    public Optional<TenantPo> findFirstByTenantNumber(Long tenantNumber);


}
