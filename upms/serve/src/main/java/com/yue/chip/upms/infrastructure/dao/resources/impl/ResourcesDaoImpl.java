package com.yue.chip.upms.infrastructure.dao.resources.impl;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.infrastructure.dao.resources.ResourcesDaoEx;
import com.yue.chip.upms.infrastructure.po.resources.ResourcesPo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:44
 * @description ResourcesDaoImpl
 */
public class ResourcesDaoImpl implements ResourcesDaoEx {

    @Autowired
    private BaseDao<ResourcesPo> baseDao;
}
