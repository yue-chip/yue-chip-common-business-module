package com.xiao.wei.security.domain.aggregates.device;

import com.xiao.wei.definition.tenant.DeviceTenantDefinition;
import com.xiao.wei.security.domain.repository.device.DeviceTenantRepository;
import com.yue.chip.annotation.YueChipDDDEntity;
import com.yue.chip.utils.AssertUtil;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

/**
 * @author coby
 * 
 * @date 2024/1/31 下午2:20
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@YueChipDDDEntity
public class DeviceTenant extends DeviceTenantDefinition {

    @Resource
    private static DeviceTenantRepository deviceTenantRepository;

    public Boolean checkSnIsExist() {
        AssertUtil.hasText(getSn(),"设备串码不能为空");
        Optional<DeviceTenant> optional = deviceTenantRepository.findDeviceTenantByDeviceSn(getSn());
        return optional.isPresent();
    }
}
