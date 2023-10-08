package com.yue.chip.upms.domain.repository.organizational;

import com.yue.chip.upms.domain.aggregates.Organizational;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalPo;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalUserPo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/7 下午6:16
 */
public interface OrganizationalRepository {

    /**
     * 根据用户ID查询关联的组织机构
     *
     * @param userId
     * @return
     */
    public Optional<Organizational> findByUserId(@NotNull Long userId);

    /**
     * 删除用户与组织机构的关联关系
     * @param userId
     */
    public void deleteOrganizationalUser(@NotNull Long userId);

    /**
     * 保存用户与组织架构的关联关系
     * @param organizationalUserPo
     */
    public void saveOrganizationalUser(@NotNull OrganizationalUserPo organizationalUserPo);

    /**
     * 保存组织架构
     * @param organizational
     */
    public void saveOrganizational(@NotNull OrganizationalPo organizational);

    /**
     * 修改组织机构
     * @param organizational
     */
    public void updateOrganizational(@NotNull OrganizationalPo organizational);

    /**
     * 根据父节点id和机构名称查询机构
     * @param parentId
     * @param name
     * @return
     */
    public Optional<Organizational> findByParentIdAndName(@NotNull Long parentId, @NotBlank String name);
}
