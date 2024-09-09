package com.yue.chip.upms.application.service.impl;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.upms.application.service.TenantApplication;
import com.yue.chip.upms.assembler.tenant.TenantMapper;
import com.yue.chip.upms.domain.aggregates.Organizational;
import com.yue.chip.upms.domain.aggregates.Tenant;
import com.yue.chip.upms.domain.repository.tenant.TenantRepository;
import com.yue.chip.upms.domain.service.tenant.TenantService;
import com.yue.chip.upms.infrastructure.po.tenant.TenantPo;
import com.yue.chip.upms.interfaces.dto.tenant.TenantAddDTO;
import com.yue.chip.upms.interfaces.dto.tenant.TenantUpdateDTO;
import com.yue.chip.utils.AssertUtil;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    @Transactional(rollbackFor = {Exception.class})
    public void delete(List<Long> ids) {
        ids.forEach(id->{
            //删除租户
            tenantRepository.deleteTenant(id);
            //更新其它租户数据中的租户状态
            tenantRepository.updateOtherDataBase(State.DISABLE,id);
        });
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public TenantPo save(TenantAddDTO tenantAddDTO) {
        //判断租户名称时候存在
        AssertUtil.isTrue(Tenant.builder().name(tenantAddDTO.getName()).build().checkNameIsExist(),"该租户名称已存在");
        //判断租户登录域存不存在
        AssertUtil.isTrue(tenantService.checkDomainIsExist(null, tenantAddDTO.getRequestDomain()),"该登录域已存在");
        //保存租户
        TenantPo tenantPo = tenantMapper.toTenantPo(tenantAddDTO);
        tenantPo.setState(State.NORMAL);
        tenantPo.setIsDefault(false);
        TenantPo entity = tenantRepository.saveTenant(tenantPo);
        tenantRepository.updateTenantNumber(entity.getId(),entity.getId());
        //创建租户数据库
        tenantService.createTenantDatabase(entity.getId());
        //租户信息保存到redis
        tenantService.saveToRedis(tenantMapper.toTenant(entity));
        return entity;
    }

    @Override
    public void update(TenantUpdateDTO tenantUpdateDTO) {
        //判断租户名称时候存在
        AssertUtil.isTrue(Tenant.builder().id(tenantUpdateDTO.getId()).name(tenantUpdateDTO.getName()).build().checkNameIsExist(),"该租户名称已存在");
        //判断租户登录域存不存在
        AssertUtil.isTrue(tenantService.checkDomainIsExist(tenantUpdateDTO.getId(), tenantUpdateDTO.getRequestDomain()),"该登录域已存在");
        //更新租户
        tenantRepository.updateTenant(tenantMapper.toTenantPo(tenantUpdateDTO));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void update(@NotNull Long id, State state) {
        TenantPo tenantPo = TenantPo.builder()
                        .state(state)
                        .id(id)
                        .build();
        tenantRepository.updateTenant(tenantPo);
        //更新其它租户数据中的租户状态
        tenantRepository.updateOtherDataBase(state,id);
    }
}
