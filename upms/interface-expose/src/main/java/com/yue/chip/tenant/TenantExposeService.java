package com.yue.chip.tenant;

import com.yue.chip.tenant.vo.TenantExposeVo;

import java.util.List;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/22 下午2:05
 */
public interface TenantExposeService {

    /**
     * 查寻所有租户
     * @return
     */
    public List<TenantExposeVo> findAll();
}
