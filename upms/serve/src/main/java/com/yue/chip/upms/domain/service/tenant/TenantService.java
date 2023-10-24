package com.yue.chip.upms.domain.service.tenant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/24 下午2:11
 */
public interface TenantService {

    /**
     * 创建租户数据库
     * @param tenantNumber 租户编码-来源于t_tenant表的id
     */
    public void createTenantDatabase(@NotNull Long tenantNumber);


    /**
     * 初始化租户数据
     * @param tenantNumber 租户编码-来源于t_tenant表的id
     */
    public void initTenantData(@NotNull Long tenantNumber);
}
