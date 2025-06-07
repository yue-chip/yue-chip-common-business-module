package com.xiao.wei.security.domain.service.device;

import jakarta.validation.constraints.NotBlank;

/**
 * @author coby
 * 
 * @date 2024/1/31 下午2:40
 */
public interface DeviceTenantService {

    /**
     * 查询设备串码是否存在
     *
     * @param sn 设备串码
     */
    public void checkSnIsExist(@NotBlank String sn);

}
