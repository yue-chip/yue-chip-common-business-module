package com.yue.chip.upms.infrastructure.dao.organizational;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalUserPo;
import jakarta.validation.constraints.NotNull;

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
    public void deleteAllByUserId(@NotNull Long userId);
}
