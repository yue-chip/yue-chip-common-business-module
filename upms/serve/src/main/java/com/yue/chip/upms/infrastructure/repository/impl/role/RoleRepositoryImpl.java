package com.yue.chip.upms.infrastructure.repository.impl.role;

import com.yue.chip.core.repository.impl.BaseRepositoryImpl;
import com.yue.chip.upms.domain.repository.role.RoleRepository;
import com.yue.chip.upms.infrastructure.dao.role.RoleDao;
import com.yue.chip.upms.infrastructure.dao.role.RoleResourcesDao;
import com.yue.chip.upms.infrastructure.po.role.RolePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:48
 * @description RoleRepositoryImpl
 */
@Repository
public class RoleRepositoryImpl extends BaseRepositoryImpl<RolePo> implements RoleRepository {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleResourcesDao roleResourcesDao;


}
