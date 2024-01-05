package com.yue.chip.upms.application.service;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.upms.infrastructure.po.tenant.TenantPo;
import com.yue.chip.upms.interfaces.dto.tenant.TenantAddDTO;
import com.yue.chip.upms.interfaces.dto.tenant.TenantUpdateDTO;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    public void delete(@NotNull @Size(min = 1) List<Long> ids);

    /**
     * 新增租户
     * @param tenantAddDTO
     * @return
     */
    public TenantPo save(@NotNull TenantAddDTO tenantAddDTO);

    /**
     * 修改租户
     * @param tenantUpdateDTO
     */
    public void update(@NotNull TenantUpdateDTO tenantUpdateDTO);

    /**
     * 修改租户状态
     *
     * @param id
     * @param state
     */
    public void update(@NotNull Long id, @NotNull State state);
}
