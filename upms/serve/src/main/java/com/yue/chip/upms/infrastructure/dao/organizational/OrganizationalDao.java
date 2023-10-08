package com.yue.chip.upms.infrastructure.dao.organizational;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalPo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/7 下午6:18
 */
public interface OrganizationalDao extends BaseDao<OrganizationalPo> ,OrganizationalDaoEx{

    /**
     * 根据父节点id和机构名称查询
     * @param parentId
     * @param name
     * @return
     */
    public Optional<OrganizationalPo> findFirstByParentIdAndName(@NotNull Long parentId, @NotBlank String name);
}
