package com.xiao.wei.security.infrastructure.dao.device.impl;

import com.xiao.wei.security.infrastructure.dao.device.DeviceTenantDaoEx;
import com.xiao.wei.security.infrastructure.po.device.DeviceTenantPo;
import com.yue.chip.core.persistence.curd.BaseDao;
import jakarta.annotation.Resource;

/**
 * @author coby
 * 
 * @date 2024/1/30 下午3:46
 */
public class DeviceTenantDaoImpl implements DeviceTenantDaoEx {

    @Resource
    private BaseDao<DeviceTenantPo> baseDao;
}
