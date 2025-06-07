package com.xiao.wei.security.infrastructure.dao.device;

import com.xiao.wei.security.infrastructure.po.device.DeviceTenantPo;
import com.yue.chip.core.persistence.curd.BaseDao;
import jakarta.validation.constraints.NotBlank;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author coby
 * 
 * @date 2024/1/30 下午3:45
 */
public interface DeviceTenantDao extends BaseDao<DeviceTenantPo>,DeviceTenantDaoEx {

    /**
     * 根据设备串码获取设备与租户的关联关系
     * @param sn
     * @return
     */
    public Optional<DeviceTenantPo> findFirstBySn(@NotBlank String sn);

    /**
     * 删除设备与租户的关联关系
     * @param sn
     * @return
     */
    @Transactional
    public int deleteAllBySn(@NotBlank String sn);
}
