package com.yue.chip.upms.infrastructure.dao.resources;

import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.infrastructure.po.resources.ResourcesPo;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:44
 * @description ResourcesDaoEx
 */
public interface ResourcesDaoEx {

    /**
     * 根据用户/父节点查询
     * @param userId
     * @param parentId
     * @param scope
     * @return
     */
    public List<ResourcesPo> find(Long userId, Long parentId, Scope scope);

    /**
     * 根据角色id查询关联的资源
     * @param roleId
     * @return
     */
    public List<ResourcesPo> find(Long roleId);
}
