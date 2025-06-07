package com.xiao.wei.security.domain.repository.device;

import com.xiao.wei.security.domain.aggregates.device.DeviceTenant;
import com.xiao.wei.security.infrastructure.po.device.DeviceTenantPo;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public interface DeviceTenantRepository {

    /**
     * 根据串码查询设备租户关联关系
     * @param sn
     * @return
     */
    Optional<DeviceTenant> findDeviceTenantByDeviceSn(String sn);

    /**
     * 保存设备与租户关系
     * @param deviceTenantPo
     * @return
     */
    Optional<DeviceTenant> saveDeviceTenant(DeviceTenantPo deviceTenantPo);

    /**
     * 根据设备串码删除设备与租户关联关系
     * @param sn
     */
    public void deleteDeviceTenantBySn(@NotBlank String sn);

    /**
     * 根据串码获取设备所在的租户
     * @param sn
     * @return
     */
    public Optional<Long> getTenantNumberBySn(@NotBlank String sn);
}
