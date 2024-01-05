package com.yue.chip.upms.infrastructure.dao.tenant;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.po.tenant.TenantPo;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

    /**
     * 更新租户编码
     * @param id
     * @param tenantNumber
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "update t_tenant set tenant_number = :tenantNumber where id = :id", nativeQuery = true)
    public int updateTenantNumber(@NotNull @Param("id") Long id, @NotNull @Param("tenantNumber") Long tenantNumber);
}
