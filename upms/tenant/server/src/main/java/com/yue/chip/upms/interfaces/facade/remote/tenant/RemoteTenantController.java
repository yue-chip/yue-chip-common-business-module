package com.yue.chip.upms.interfaces.facade.remote.tenant;

import com.yue.chip.core.ResultData;
import com.yue.chip.core.tenant.RemoteTenant;
import com.yue.chip.core.tenant.common.TenantDefinition;
import com.yue.chip.upms.assembler.tenant.TenantMapper;
import com.yue.chip.upms.domain.aggregates.tenant.Tenant;
import com.yue.chip.upms.domain.repository.tenant.TenantRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/tenant")
@Validated
@Tag(name = "租户远程调用")
@Log
public class RemoteTenantController implements RemoteTenant {

    @Resource
    private TenantRepository tenantRepository;

    @Resource
    private TenantMapper tenantMapper;

    @Override
    public ResultData<TenantDefinition> get(@NotBlank String requestDomain) {
        ResultData.ResultDataBuilder<TenantDefinition> builder = ResultData.builder();
        Optional<Tenant> optional = tenantRepository.findTenantByRequestDomain(requestDomain);
        if(optional.isPresent()){
            return builder.data(tenantMapper.toTenantDefinition(optional.get())).build();
        }
        return builder.build();
    }

    @Override
    public ResultData<TenantDefinition> get(@NotNull Long tenantNumber) {
        ResultData.ResultDataBuilder<TenantDefinition> builder = ResultData.builder();
        Optional<Tenant> optional = tenantRepository.findTenantByTenantNumber(tenantNumber);
        if(optional.isPresent()){
            return builder.data(tenantMapper.toTenantDefinition(optional.get())).build();
        }
        return builder.build();
    }

    @Override
    public ResultData<List<TenantDefinition>> findAll() {
        ResultData.ResultDataBuilder<List<TenantDefinition>> builder = ResultData.builder();
        List<Tenant> tenants = tenantRepository.findAll();
        return builder.data(tenantMapper.toTenantDefinition(tenants)).build();
    }
}
