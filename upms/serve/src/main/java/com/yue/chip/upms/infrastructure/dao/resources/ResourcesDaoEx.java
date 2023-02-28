package com.yue.chip.upms.infrastructure.dao.resources;

import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.infrastructure.po.resources.ResourcesPo;

import java.util.List;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:44
 * @description ResourcesDaoEx
 */
public interface ResourcesDaoEx {

    public List<ResourcesPo> findByUserId(Long userId, Long parentId, Scope scope);
}
