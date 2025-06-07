package com.xiao.wei.security.assembler.device;

import com.xiao.wei.definition.tenant.DeviceTenantDefinition;
import com.xiao.wei.security.domain.aggregates.device.DeviceTenant;
import com.xiao.wei.security.infrastructure.po.device.DeviceTenantPo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author coby
 * 
 * @date 2024/1/31 下午2:23
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeviceTenantMapper {

    DeviceTenantMapper INSTANCE = Mappers.getMapper(DeviceTenantMapper.class);

    DeviceTenant toDeviceTenant(DeviceTenantPo deviceTenantPo);

    DeviceTenantPo toDeviceTenantPo(DeviceTenantDefinition deviceTenantDefinition);

    DeviceTenantDefinition toDeviceTenantDefinition(DeviceTenant deviceTenant);
}
