package com.yue.chip.upms.infrastructure.dao.organizational;

import com.yue.chip.upms.domain.aggregates.Organizational;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalPo;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/8 下午3:04
 */
public interface OrganizationalDaoEx {

    /**
     * 根据用户id查询关联的组织机构
     *
     * @param userId
     * @return
     */
    public Optional<OrganizationalPo> findByUserId(@NotNull Long userId);

}
