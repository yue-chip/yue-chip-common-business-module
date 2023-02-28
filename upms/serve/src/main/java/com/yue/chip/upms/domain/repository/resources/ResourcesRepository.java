package com.yue.chip.upms.domain.repository.resources;

import com.yue.chip.core.repository.BaseRepository;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.infrastructure.po.resources.ResourcesPo;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTree;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:48
 * @description ResourcesRepository
 */
public interface ResourcesRepository extends BaseRepository<ResourcesPo> {

    /**
     * 根据用户id查询树形结构权限
     *
     * @param userId
     * @param parentId
     * @param scope
     * @return
     */
    public List<ResourcesTree> findResourcesToTree(Long userId, Long parentId, Scope scope);
}
