//package com.xiao.wei.iot;
//
//import com.xiao.wei.definition.tenant.DeviceTenantDefinition;
//import com.yue.chip.core.Optional;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//
//public interface DeviceTenantServiceExpose {
//
//    /**
//     * 根据设备串码获取租户
//     * @param sn    设备串码
//     * @return
//     */
//    public Optional<DeviceTenantDefinition> getDeviceTenantBySn(@NotBlank String sn);
//
//    /**
//     * 保存设备和租户的关系
//     * @param deviceTenantDefinition
//     * @return
//     */
//    public Optional<DeviceTenantDefinition> saveDeviceTenant(@NotNull DeviceTenantDefinition deviceTenantDefinition);
//
//    /**
//     * 删除设备与租户关联关系
//     * @param sn
//     */
//    public void deleteDeviceTenantBySn(@NotBlank String sn);
//
//    /**
//     * 获取设备所在的租户编码
//     * @param sn
//     * @return
//     */
//    public Optional<Long> getTenantNumberBySn(@NotBlank String sn);
//
//}
