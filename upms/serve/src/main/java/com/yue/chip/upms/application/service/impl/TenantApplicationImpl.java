package com.yue.chip.upms.application.service.impl;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.exception.BusinessException;
import com.yue.chip.upms.application.service.TenantApplication;
import com.yue.chip.upms.assembler.tenant.TenantMapper;
import com.yue.chip.upms.domain.aggregates.Tenant;
import com.yue.chip.upms.domain.repository.tenant.TenantRepository;
import com.yue.chip.upms.domain.service.tenant.TenantService;
import com.yue.chip.upms.infrastructure.po.tenant.TenantPo;
import com.yue.chip.upms.interfaces.dto.tenant.TenantAddDTO;
import com.yue.chip.upms.interfaces.dto.tenant.TenantUpdateDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/24 上午10:54
 */
@Service
public class TenantApplicationImpl implements TenantApplication {

    @Resource
    private TenantRepository tenantRepository;

    @Resource
    private TenantService tenantService;

    @Resource
    private TenantMapper tenantMapper;

    @Override
    public void delete(List<Long> ids) {

    }

    @Override
    public TenantPo save(TenantAddDTO tenantAddDTO) {
        //判断租户名称时候存在
        if (Tenant.builder().name(tenantAddDTO.getName()).build().checkNameIsExist()) {
            BusinessException.throwException("该租户名称已存在");
        }
        //保存租户
        TenantPo tenantPo = tenantMapper.toTenantPo(tenantAddDTO);
        tenantPo.setState(State.NORMAL);
        TenantPo entity = tenantRepository.save(tenantPo);
        //创建租户数据库
        tenantService.createTenantDatabase(entity.getId());
        //初始化数据
        tenantService.initTenantData(entity.getId());
        return entity;
    }

    @Override
    public void update(TenantUpdateDTO tenantUpdateDTO) {

    }
}
