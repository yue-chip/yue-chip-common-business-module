package com.yue.chip.upms.infrastructure.dao.resources;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.infrastructure.po.resources.ResourcesPo;

import java.util.List;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:33
 * @description ResourcesDao
 */
public interface ResourcesDao extends BaseDao<ResourcesPo>, ResourcesDaoEx {

    public List<ResourcesPo> findByParentIdAndScopeOrderBySort(Long parentId, Scope scope);

    public Optional<ResourcesPo> findFirstByCode(String code);

    public Optional<ResourcesPo> findFirstByName(String name);

    public Optional<ResourcesPo> findFirstByNameAndParentId(String name, Long parentId);

    public List<ResourcesPo> findByParentId(Long parentId);

    public Optional<ResourcesPo> findFirstByUrl(String url);

}
