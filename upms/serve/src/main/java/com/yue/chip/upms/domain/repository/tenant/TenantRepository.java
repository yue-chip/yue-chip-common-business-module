package com.yue.chip.upms.domain.repository.tenant;

import com.sun.jna.platform.win32.WinDef;
import com.yue.chip.core.IPageResultData;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.upms.domain.aggregates.Tenant;
import com.yue.chip.upms.infrastructure.po.tenant.TenantPo;
import com.yue.chip.upms.interfaces.vo.tenant.TenantVo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/24 上午10:52
 */
public interface TenantRepository {

    /**
     * 列表查询
     * @param name
     * @param manager
     * @param state
     * @param phoneNumber
     * @param pageable
     * @return
     */
    public IPageResultData<List<TenantVo>> list(String name, String manager, State state, String phoneNumber, YueChipPage pageable);

    /**
     * 新增
     * @param tenantPo
     */
    public TenantPo save(@NotNull TenantPo tenantPo);

    /**
     * 修改
     * @param tenantPo
     */
    public void update(@NotNull TenantPo tenantPo);

    /**
     * 删除
     *
     * @param id
     */
    public void delete(@NotNull Long id);

    /**
     * 根据名称查询
     * @param name
     * @return
     */
    public Optional<Tenant> findByName(@NotBlank String name);

    /**
     * 更新租户状态
     * @param state
     * @param id
     */
    public void updateTenantState(@NotNull State state, @NotNull Long id);

    /**
     * 更新其它租户数据库中的租户状态
     * @param state
     * @param tenantNumber
     */
    public void updateOtherDataBase(@NotNull State state, @NotNull Long tenantNumber);
}
