//package com.yue.chip.upms.application.expose.impl.tenant;
//
//import com.yue.chip.core.tenant.TenantExposeService;
//import com.yue.chip.core.tenant.common.TenantDefinition;
//import com.yue.chip.upms.assembler.tenant.TenantMapper;
//import com.yue.chip.upms.domain.aggregates.tenant.Tenant;
//import com.yue.chip.upms.domain.repository.tenant.TenantRepository;
//import jakarta.annotation.Resource;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import org.apache.dubbo.config.annotation.DubboService;
//
//import java.util.Optional;
//
//@DubboService(interfaceClass = TenantExposeService.class)
//public class TenantExposeServiceImpl implements TenantExposeService {
//
//    @Resource
//    private TenantRepository tenantRepository;
//
//    @Resource
//    private TenantMapper tenantMapper;
//
//
//    @Override
//    public com.yue.chip.core.Optional<TenantDefinition> getTenantDefinition(@NotBlank String url) {
//        Optional<Tenant> optional = tenantRepository.findTenantByUrl(url);
//        if(optional.isPresent()){
//            return com.yue.chip.core.Optional.builder().build().ofNullable(tenantMapper.toTenantDefinition(optional.get()));
//        }
//        return com.yue.chip.core.Optional.empty();
//    }
//
//    @Override
//    public com.yue.chip.core.Optional<TenantDefinition> findTenantByTenantNumber(@NotNull Long tenantNumber) {
//        Optional<Tenant> optional = tenantRepository.findTenantByTenantNumber(tenantNumber);
//        if(optional.isPresent()){
//            return com.yue.chip.core.Optional.builder().build().ofNullable(tenantMapper.toTenantDefinition(optional.get()));
//        }
//        return com.yue.chip.core.Optional.empty();
//    }
//}
//
