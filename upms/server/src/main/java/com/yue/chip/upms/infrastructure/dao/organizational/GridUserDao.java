package com.yue.chip.upms.infrastructure.dao.organizational;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.po.organizational.GridUserPo;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2024-05-29
 */
public interface GridUserDao extends BaseDao<GridUserPo> {
    List<GridUserPo> findAllByGridId(Long gridId);
    List<GridUserPo> findAllByGridIdIn(List<Long> gridIds);

    @Modifying
    @Transactional
    @Query("DELETE FROM GridUserPo e WHERE e.id IN :ids")
    void deleteByIds(@Param("ids") List<Long> ids);
}
