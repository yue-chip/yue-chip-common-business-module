package com.xiao.wei.security.application.service.device.impl;

import com.security.tenant.DeviceTenantServiceExpose;
import com.xiao.wei.definition.tenant.DeviceTenantDefinition;
import com.xiao.wei.security.assembler.device.DeviceTenantMapper;
import com.xiao.wei.security.domain.aggregates.device.DeviceTenant;
import com.xiao.wei.security.domain.repository.device.DeviceTenantRepository;
import com.yue.chip.core.Optional;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(interfaceClass = DeviceTenantServiceExpose.class)
public class DeviceTenantServiceExposeImpl implements DeviceTenantServiceExpose {

    @Resource
    private DeviceTenantRepository deviceTenantRepository;
    @Resource
    private DeviceTenantMapper deviceTenantMapper;


    @Override
    public Optional<DeviceTenantDefinition> getDeviceTenantBySn(@NotBlank String sn) {
        java.util.Optional<DeviceTenant> deviceTenantOptional = deviceTenantRepository.findDeviceTenantByDeviceSn(sn);
        if (deviceTenantOptional.isPresent()) {
            return Optional.builder().build().ofNullable(deviceTenantMapper.toDeviceTenantDefinition(deviceTenantOptional.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<DeviceTenantDefinition> saveDeviceTenant(@NotNull DeviceTenantDefinition deviceTenantDefinition) {
        java.util.Optional<DeviceTenant> optional = deviceTenantRepository.saveDeviceTenant(deviceTenantMapper.toDeviceTenantPo(deviceTenantDefinition));
        if (optional.isPresent()) {
            return Optional.builder().build().ofNullable(deviceTenantMapper.toDeviceTenantDefinition(optional.get()));
        }
        return Optional.empty();
    }

    @Override
    public void deleteDeviceTenantBySn(@NotBlank String sn) {
        deviceTenantRepository.deleteDeviceTenantBySn(sn);
    }

    @Override
    public Optional<Long> getTenantNumberBySn(@NotBlank String sn) {
        java.util.Optional<Long> optional = deviceTenantRepository.getTenantNumberBySn(sn);
        if (optional.isPresent()) {
            return Optional.builder().build().of(optional.get());
        }
        return Optional.empty();
    }

}
