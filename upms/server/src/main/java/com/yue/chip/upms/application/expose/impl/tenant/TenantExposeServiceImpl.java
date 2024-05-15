package com.yue.chip.upms.application.expose.impl.tenant;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.tenant.TenantExposeService;
import com.yue.chip.tenant.vo.TenantExposeVo;
import com.yue.chip.upms.assembler.tenant.TenantMapper;
import com.yue.chip.upms.domain.aggregates.Tenant;
import com.yue.chip.upms.domain.repository.tenant.TenantRepository;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/22 下午2:07
 */
@DubboService(interfaceClass = TenantExposeService.class)
public class TenantExposeServiceImpl implements TenantExposeService {

    @Resource
    private TenantRepository tenantRepository;

    @Resource
    private TenantMapper tenantMapper;

    @Override
    public List<TenantExposeVo> findAll() {
        List<Tenant> list = tenantRepository.findAllTenant(State.NORMAL);
        return tenantMapper.toTenantExposeVo(list);
    }
}
