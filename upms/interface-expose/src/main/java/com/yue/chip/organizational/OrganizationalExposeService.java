package com.yue.chip.organizational;

import com.yue.chip.core.Optional;
import com.yue.chip.organizational.vo.OrganizationalExposeVo;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2023-10-28
 */
public interface OrganizationalExposeService {

    /**
     * 根据id查询机构
     *
     * @param id
     * @return
     */
    public Optional<OrganizationalExposeVo> findById(@NotNull Long id);

    /**
     * 查询机构id数组信息
     * @param ids
     * @return
     */
    public List<OrganizationalExposeVo> findByIdList(Set<Long> ids);

    public List<OrganizationalExposeVo> findAll();

    /**
     * 获取所有节点数据
     * @return
     */
    public List<OrganizationalExposeVo> findAllChildrenByOrganizationalId(Long organizationalId);

    /**
     * 根据用户获取所在机构和所有子节点
     * @param userId
     * @return
     */
    public List<OrganizationalExposeVo> findAllChildrenByUserId(Long userId);

    /**
     * 根据当前用户获取所在机构和所有子节点
     * @return
     */
    public List<OrganizationalExposeVo> findAllChildrenByCurrentUserId();

    public Set<Long> findAllChildrenOrganizationalIds(Long parentId);

    public List<OrganizationalExposeVo> findChildrenOrganizationalIds(Long parentId);

}
