package com.yue.chip.upms.domain.service.upms;

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
    public void roleResources(Long roleId,Long[] resourcesIds);
}
