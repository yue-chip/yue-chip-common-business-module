package com.yue.chip.upms.assembler.tenant;

import com.yue.chip.tenant.vo.TenantExposeVo;
import com.yue.chip.upms.domain.aggregates.Tenant;
import com.yue.chip.upms.infrastructure.po.tenant.TenantPo;
import com.yue.chip.upms.interfaces.dto.tenant.TenantAddDTO;
import com.yue.chip.upms.interfaces.dto.tenant.TenantUpdateDTO;
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

    public TenantVo toTenantVo(TenantPo tenantPo);

    public TenantVo toTenantVo(Tenant tenant);

    public TenantPo toTenantPo(TenantAddDTO tenantAddDTO);

    public TenantPo toTenantPo(TenantUpdateDTO tenantUpdateDTO);

    public List<TenantVo> toTenantVo(List<TenantPo> list);

    public List<Tenant> toTenant(List<TenantPo> list);

    public TenantExposeVo toTenantExposeVo(Tenant tenant);

    public List<TenantExposeVo> toTenantExposeVo(List<Tenant> tenantList);
}
