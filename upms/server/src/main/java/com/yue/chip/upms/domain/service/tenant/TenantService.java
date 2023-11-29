package com.yue.chip.upms.domain.service.tenant;

import com.yue.chip.upms.domain.aggregates.Tenant;
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

    /**
     * 保存租户信息到redis
     * @param tenant
     */
    public void saveToRedis(@NotNull Tenant tenant);

    /**
     * 保存租户信息到redis
     */
    public void saveAllToRedis();

    /**
     * 判断租户登录域是否存在
     *
     * @param id
     * @param domain
     * @return
     */
    public Boolean checkDomainIsExist(Long id,@NotBlank String domain);
}
