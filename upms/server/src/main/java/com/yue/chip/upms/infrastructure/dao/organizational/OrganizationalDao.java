package com.yue.chip.upms.infrastructure.dao.organizational;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalPo;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    /**
     * 根据父节点id查询子节点
     * @param parentId
     * @return
     */
    public List<OrganizationalPo> findAllByParentIdAndStateOrderBySortAsc(@NotNull Long parentId,@NotNull State state);

    /**
     * 根据父节点id查询子节点
     * @param parentId
     * @return
     */
    public List<OrganizationalPo> findAllByParentIdOrderBySortAsc(@NotNull Long parentId);

    /**
     * 查询子部门
     * @param parentId
     * @return
     */
    public List<OrganizationalPo> findAllByParentId(@NotNull Long parentId);

    /**
     * 更新机构负责人
     *
     * @param organizationalId
     * @param leaderId
     * @return
     */
    @Modifying
    @Transactional
    @Query("update OrganizationalPo set leaderId = :leaderId where id = :id ")
    public int updateLeader(@NotNull @Param("id") Long organizationalId,@Param("leaderId")  Long leaderId);

    /**
     * 删除机构负责人
     * @param leaderId
     * @return
     */
    @Modifying
    @Transactional
    @Query("update OrganizationalPo set leaderId = null where leaderId = :leaderId ")
    public int deleteLeader(@NotNull @Param("leaderId")   Long leaderId);

    public List<OrganizationalPo> findAllByIdIn(Set<Long> Ids);

    List<OrganizationalPo> findAllByNameIn(Set<String> names);

}
