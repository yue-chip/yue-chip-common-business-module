package com.yue.chip.upms.infrastructure.repository.tenant.impl;

import com.yue.chip.core.IPageResultData;
import com.yue.chip.core.PageResultData;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.upms.assembler.tenant.TenantMapper;
import com.yue.chip.upms.domain.aggregates.Tenant;
import com.yue.chip.upms.domain.repository.tenant.TenantRepository;
import com.yue.chip.upms.infrastructure.dao.tenant.TenantDao;
import com.yue.chip.upms.infrastructure.dao.tenant.TenantStateDao;
import com.yue.chip.upms.infrastructure.po.tenant.TenantPo;
import com.yue.chip.upms.interfaces.vo.tenant.TenantVo;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/24 上午10:56
 */
@Component
public class TenantRepositoryImpl implements TenantRepository {

    @Resource
    private TenantDao tenantDao;

    @Resource
    private TenantStateDao tenantStateDao;

    @Resource
    private TenantMapper tenantMapper;

    @Override
    public IPageResultData<List<TenantVo>> list(String name, String manager, State state, String phoneNumber, YueChipPage pageable) {
        Page<TenantPo> page = tenantDao.list(name,manager,state,phoneNumber,pageable);
        IPageResultData pageResultData = PageResultData.convert(page,tenantMapper.toTenant(page.getContent()));
        return pageResultData;
    }

    @Override
    public TenantPo save(TenantPo tenantPo) {
        return tenantDao.save(tenantPo);
    }

    @Override
    public void update(TenantPo tenantPo) {
        tenantDao.update(tenantPo);
    }

    @Override
    public void delete(Long id) {
        tenantDao.deleteById(id);
    }

    @Override
    public Optional<Tenant> findByName(String name) {
        Optional<TenantPo> optional = tenantDao.findFirstByName(name);
        if (optional.isPresent()) {
            return Optional.ofNullable(tenantMapper.toTenant(optional.get()));
        }
        return Optional.empty();
    }
}
