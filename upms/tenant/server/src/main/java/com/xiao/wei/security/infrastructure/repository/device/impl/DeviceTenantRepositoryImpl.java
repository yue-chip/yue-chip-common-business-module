package com.xiao.wei.security.infrastructure.repository.device.impl;

import com.xiao.wei.security.assembler.device.DeviceTenantMapper;
import com.xiao.wei.security.domain.aggregates.device.DeviceTenant;
import com.xiao.wei.security.domain.repository.device.DeviceTenantRepository;
import com.xiao.wei.security.infrastructure.dao.device.DeviceTenantDao;
import com.xiao.wei.security.infrastructure.po.device.DeviceTenantPo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceTenantRepositoryImpl implements DeviceTenantRepository {

    @Resource
    private DeviceTenantDao deviceTenantDao;
    @Resource
    private  DeviceTenantMapper deviceTenantMapper;

    @Override
    public Optional<DeviceTenant> findDeviceTenantByDeviceSn(String sn) {
        Optional<DeviceTenantPo> optional = deviceTenantDao.findFirstBySn(sn);
        if (optional.isPresent()) {
            return Optional.of(deviceTenantMapper.toDeviceTenant(optional.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<DeviceTenant> saveDeviceTenant(DeviceTenantPo deviceTenantPo) {
        deviceTenantPo = deviceTenantDao.save(deviceTenantPo);
        return Optional.ofNullable(deviceTenantMapper.toDeviceTenant(deviceTenantPo));
    }

    @Override
    public void deleteDeviceTenantBySn(String sn) {
        deviceTenantDao.deleteAllBySn(sn);
    }

    @Override
    public Optional<Long> getTenantNumberBySn(String sn) {
        Optional<DeviceTenantPo> optional = deviceTenantDao.findFirstBySn(sn);
        if (optional.isPresent()) {
            return Optional.ofNullable(optional.get().getTenantNumber());
        }
        return Optional.empty();
    }
}
