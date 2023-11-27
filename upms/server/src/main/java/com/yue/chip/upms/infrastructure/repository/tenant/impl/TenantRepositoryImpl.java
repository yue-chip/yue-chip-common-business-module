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
import com.yue.chip.upms.infrastructure.po.tenant.TenantStatePo;
import com.yue.chip.upms.interfaces.vo.tenant.TenantVo;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
        IPageResultData pageResultData = PageResultData.convert(page,tenantMapper.toTenantVo(page.getContent()));
        return pageResultData;
    }

    @Override
    public TenantPo saveTenant(TenantPo tenantPo) {
        return tenantDao.save(tenantPo);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class},propagation = Propagation.REQUIRES_NEW)
    public void updateTenant(TenantPo tenantPo) {
        tenantDao.update(tenantPo);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class},propagation = Propagation.REQUIRES_NEW)
    public void deleteTenant(Long id) {
        tenantDao.deleteById(id);
    }

    @Override
    public Optional<Tenant> findTenantByName(String name) {
        Optional<TenantPo> optional = tenantDao.findFirstByName(name);
        if (optional.isPresent()) {
            return Optional.ofNullable(tenantMapper.toTenant(optional.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<Tenant> findAllTenant(State state) {
        List<TenantPo> list = tenantDao.findAllByState(state);
        return tenantMapper.toTenant(list);
    }

    @Override
    public void updateTenantState(State state, Long id) {
        TenantPo tenantPo = TenantPo.builder()
                .state(state)
                .id(id)
                .build();
        tenantDao.update(tenantPo);
    }

    @Override
    public void updateOtherDataBase(State state, Long tenantNumber) {
        tenantDao.updateOtherDataBase(state,tenantNumber);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class},propagation = Propagation.REQUIRES_NEW)
    public void insertOtherDataBase(State state, Long tenantNumber) {
        tenantDao.insertOtherDataBase(state,tenantNumber);
    }

    @Override
    public Optional<TenantStatePo> findTenantStateFirst() {
        List<TenantStatePo> list = tenantStateDao.findAll();
        if (list.size()>0){
            return Optional.ofNullable(list.get(0));
        }
        return Optional.empty();
    }

    @Override
    public Optional<TenantVo> tenantDetails(Long id) {
        Optional<TenantPo> optional = tenantDao.findById(id);
        if (optional.isPresent()) {
            return Optional.ofNullable(tenantMapper.toTenantVo(optional.get()));
        }
        return Optional.empty();
    }
}
