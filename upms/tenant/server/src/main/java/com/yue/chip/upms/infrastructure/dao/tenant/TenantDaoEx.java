package com.yue.chip.upms.infrastructure.dao.tenant;

import com.yue.chip.upms.infrastructure.po.tenant.TenantPo;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public interface TenantDaoEx {

    /**
     * 根据请求域名查询租户信息
     * @param requestDomain
     * @return
     */
    public Optional<TenantPo> findTenantByUrl(@NotBlank String requestDomain);

}
