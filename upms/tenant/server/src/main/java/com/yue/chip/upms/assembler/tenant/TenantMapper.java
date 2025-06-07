package com.yue.chip.upms.assembler.tenant;

import com.yue.chip.core.tenant.common.TenantDefinition;
import com.yue.chip.upms.domain.aggregates.tenant.Tenant;
import com.yue.chip.upms.infrastructure.po.tenant.TenantPo;
import com.yue.chip.upms.interfaces.vo.tenant.TenantVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/24 上午10:53
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TenantMapper {

    TenantMapper INSTANCE = Mappers.getMapper(TenantMapper.class);

    public Tenant toTenant(TenantPo tenantPo);

    public TenantDefinition toTenantDefinition(Tenant tenant);

    public List<TenantDefinition> toTenantDefinition(List<Tenant> tenants);

    public TenantVo toTenantVo(TenantPo tenantPo);

    public TenantVo toTenantVo(Tenant tenant);

    public List<TenantVo> toTenantVo(List<TenantPo> list);

    public List<Tenant> toTenant(List<TenantPo> list);

}
