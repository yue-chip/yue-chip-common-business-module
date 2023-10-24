package com.yue.chip.upms.application.service;

import com.yue.chip.upms.infrastructure.po.tenant.TenantPo;
import com.yue.chip.upms.interfaces.dto.tenant.TenantAddDTO;
import com.yue.chip.upms.interfaces.dto.tenant.TenantUpdateDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/24 上午10:54
 */
public interface TenantApplication {

    /**
     * 删除租户
     * @param ids
     */
    public void delete(@NotNull List<Long> ids);

    /**
     * 新增租户
     * @param tenantPo
     * @return
     */
    public TenantPo save(TenantAddDTO tenantAddDTO);

    /**
     * 修改用户
     * @param tenantUpdateDTO
     */
    public void update(TenantUpdateDTO tenantUpdateDTO);
}
