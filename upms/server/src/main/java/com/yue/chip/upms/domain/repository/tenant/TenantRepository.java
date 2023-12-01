package com.yue.chip.upms.domain.repository.tenant;

import com.yue.chip.core.IPageResultData;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.upms.domain.aggregates.Tenant;
import com.yue.chip.upms.infrastructure.po.tenant.TenantPo;
import com.yue.chip.upms.infrastructure.po.tenant.TenantStatePo;
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
    public TenantPo saveTenant(@NotNull TenantPo tenantPo);

    /**
     * 修改
     * @param tenantPo
     */
    public void updateTenant(@NotNull TenantPo tenantPo);

    /**
     * 更新租户编码
     * @param tenantId
     * @param tenantNumber
     */
    public void updateTenantNumber(@NotNull Long tenantId,@NotNull Long tenantNumber);

    /**
     * 删除
     *
     * @param id
     */
    public void deleteTenant(@NotNull Long id);

    /**
     * 根据名称查询
     * @param name
     * @return
     */
    public Optional<Tenant> findTenantByName(@NotBlank String name);

    /**
     * 根据租户编码获取租户
     * @param tenantNumber
     * @return
     */
    public Optional<Tenant> findTenantByTenantNumber(Long tenantNumber);

    /**
     * 查寻所有租户(特殊场景使用 jdbc查寻 禁止其它功能调用)
     * @param state
     * @return
     */
    public List<Tenant> findAllTenant(@NotNull State state);


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


    /**
     * 保存其它租户数据库中的租户状态
     * @param state
     * @param tenantNumber
     */
    public void insertOtherDataBase(@NotNull State state, @NotNull Long tenantNumber);

    /**
     * 查询当前租户状态
     *
     * @return
     */
    public Optional<TenantStatePo> findTenantStateFirst();

    /**
     * 获取租户详情
     *
     * @param id
     * @return
     */
    public Optional<Tenant> tenantDetails(@NotNull Long id);
}
