package com.yue.chip.upms.domain.service.upms;

import jakarta.validation.constraints.NotNull;

/**
 * @author Mr.Liu
 * @date 2023/3/6 上午11:26
 */
public interface UpmsDomainService {

    /**
     * 绑定角色资源
     * @param roleId
     * @param resourcesIds
     */
    public void roleResources(@NotNull Long roleId, Long[] resourcesIds);
}
