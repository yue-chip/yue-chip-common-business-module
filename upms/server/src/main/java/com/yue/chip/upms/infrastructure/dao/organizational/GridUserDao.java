package com.yue.chip.upms.infrastructure.dao.organizational;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.po.organizational.GridUserPo;

import java.util.List;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2024-05-29
 */
public interface GridUserDao extends BaseDao<GridUserPo> {
    List<GridUserPo> findAllByGridId(Long gridId);
}
