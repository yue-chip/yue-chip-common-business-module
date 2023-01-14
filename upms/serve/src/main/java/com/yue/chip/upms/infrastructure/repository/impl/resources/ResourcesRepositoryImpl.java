package com.yue.chip.upms.infrastructure.repository.impl.resources;

import com.yue.chip.core.repository.impl.BaseRepositoryImpl;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.domain.repository.resources.ResourcesRepository;
import com.yue.chip.upms.infrastructure.dao.resources.ResourcesDao;
import com.yue.chip.upms.infrastructure.po.resources.ResourcesPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:49
 * @description ResourcesRepositoryImpl
 */
@Repository
public class ResourcesRepositoryImpl extends BaseRepositoryImpl<ResourcesPo> implements ResourcesRepository {

    @Autowired
    private ResourcesDao resourcesDao;
}
