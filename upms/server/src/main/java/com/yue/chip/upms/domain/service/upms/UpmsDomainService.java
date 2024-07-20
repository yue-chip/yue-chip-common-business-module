package com.yue.chip.upms.domain.service.upms;

import jakarta.validation.constraints.NotNull;

import java.util.List;

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

    /**
     * 绑定用户与组织机构的关联关系
     * @param userId
     * @param organizationalId
     */
    public void userOrganizational(@NotNull Long userId, List<Long> organizationalId);
}
