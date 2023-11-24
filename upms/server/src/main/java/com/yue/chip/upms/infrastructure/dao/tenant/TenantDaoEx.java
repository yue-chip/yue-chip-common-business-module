package com.yue.chip.upms.infrastructure.dao.tenant;

import com.yue.chip.core.IPageResultData;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.upms.infrastructure.po.tenant.TenantPo;
import com.yue.chip.upms.interfaces.vo.tenant.TenantVo;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/24 上午11:38
 */
public interface TenantDaoEx {

    /**
     * 列表查询
     *
     * @param name
     * @param manager
     * @param state
     * @param phoneNumber
     * @param pageable
     * @return
     */
    public Page<TenantPo> list(String name, String manager, State state, String phoneNumber, YueChipPage pageable);

    /**
     * 更新其它租户数据库中的租户状态
     * @param state
     * @param tenantNumber
     */
    public void updateOtherDataBase(@NotNull State state, @NotNull Long tenantNumber);

    /**
     * 查寻所有租户
     * @param state
     * @return
     */
    public List<TenantPo> findAllByState(@NotNull State state);

}
