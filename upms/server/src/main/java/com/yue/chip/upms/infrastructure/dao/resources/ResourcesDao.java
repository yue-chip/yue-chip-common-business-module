package com.yue.chip.upms.infrastructure.dao.resources;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.infrastructure.po.resources.ResourcesPo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:33
 * @description ResourcesDao
 */
public interface ResourcesDao extends BaseDao<ResourcesPo>, ResourcesDaoEx {

    public List<ResourcesPo> findByParentIdAndScopeOrderBySortAsc(@NotNull Long parentId, @NotNull Scope scope);

    public Optional<ResourcesPo> findFirstByCode(@NotBlank String code);

    public Optional<ResourcesPo> findFirstByName(@NotBlank String name);

    public Optional<ResourcesPo> findFirstByNameAndParentId(@NotBlank String name,@NotNull Long parentId);

    public List<ResourcesPo> findByParentId(@NotNull Long parentId);

    public Optional<ResourcesPo> findFirstByUrl(@NotBlank String url);

}
