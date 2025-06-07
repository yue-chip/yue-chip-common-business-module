package com.xiao.wei.security.domain.service.device.impl;

import com.xiao.wei.security.domain.aggregates.device.DeviceTenant;
import com.xiao.wei.security.domain.repository.device.DeviceTenantRepository;
import com.xiao.wei.security.domain.service.device.DeviceTenantService;
import com.yue.chip.utils.AssertUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author coby
 *
 * @date 2024/1/31 下午2:40
 */
@Service
public class DeviceTenantServiceImpl implements DeviceTenantService {

    @Resource
    private DeviceTenantRepository deviceTenantRepository;

    @Override
    public void checkSnIsExist(String sn) {
        DeviceTenant deviceTenant = DeviceTenant
                .builder()
                .sn(sn)
                .build();
        Boolean isExist = deviceTenant.checkSnIsExist();
        AssertUtil.isFalse(isExist,"该设备已关联租户！");
    }

}
