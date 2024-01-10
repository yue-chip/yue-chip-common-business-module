package com.yue.chip.upms.infrastructure.dao.organizational;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalUserPo;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/7 下午6:18
 */
public interface OrganizationalUserDao extends BaseDao<OrganizationalUserPo> {

    /**
     * 删除用户与组织机构关联关系
     * @param userId
     */
    @Transactional(rollbackFor = {Exception.class})
    public void deleteAllByUserId(@NotNull Long userId);

    /**
     * 删除用户与组织机构的关系
     * @param organizationalId
     */
    @Transactional(rollbackFor = {Exception.class})
    public void deleteAllByOrganizationalId(@NotNull Long organizationalId);

    /**
     * 根据机构id和userId查询
     * @param organizationalId
     * @param userId
     * @return
     */
     List<OrganizationalUserPo> findAllByOrganizationalIdAndUserIdIn(@NotNull Long organizationalId, @NotNull Set<Long> userId);

    /**
     * 根据机构id和userId查询
     * @param userId
     * @return
     */
    List<OrganizationalUserPo> findAllByUserIdIn(@NotNull Set<Long> userId);

    List<OrganizationalUserPo> findAllByOrganizationalIdIn(List<Long> organizationalIds);
}
