package com.yue.chip.upms.interfaces.facade.remote.http.tenant;

import com.yue.chip.core.ResultData;
import com.yue.chip.core.tenant.common.TenantDefinition;
import com.yue.chip.core.tenant.remote.http.RemoteTenantDefinition;
import com.yue.chip.upms.assembler.tenant.TenantMapper;
import com.yue.chip.upms.domain.aggregates.tenant.Tenant;
import com.yue.chip.upms.domain.repository.tenant.TenantRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping()
@Validated
@Tag(name = "租户远程调用")
@Log
public class RemoteTenantController implements RemoteTenantDefinition {

    @Resource
    private TenantRepository tenantRepository;

    @Resource
    private TenantMapper tenantMapper;

    @Override
    @GetMapping(PREFIX+GET_BY_REQUEST_DOMAIN)
    public ResultData<TenantDefinition> get( String requestDomain) {
        ResultData.ResultDataBuilder<TenantDefinition> builder = ResultData.builder();
        Optional<Tenant> optional = tenantRepository.findTenantByRequestDomain(requestDomain);
        if(optional.isPresent()){
            return builder.data(tenantMapper.toTenantDefinition(optional.get())).build();
        }
        return builder.build();
    }

    @Override
    @GetMapping(PREFIX+GET_BY_TENANT_NUMBER)
    public ResultData<TenantDefinition> get( Long tenantNumber) {
        ResultData.ResultDataBuilder<TenantDefinition> builder = ResultData.builder();
        Optional<Tenant> optional = tenantRepository.findTenantByTenantNumber(tenantNumber);
        if(optional.isPresent()){
            return builder.data(tenantMapper.toTenantDefinition(optional.get())).build();
        }
        return builder.build();
    }

    @Override
    @GetMapping(GET_ALL)
    public ResultData<List<TenantDefinition>> getAll() {
        ResultData.ResultDataBuilder<List<TenantDefinition>> builder = ResultData.builder();
        List<Tenant> tenants = tenantRepository.findAll();
        return builder.data(tenantMapper.toTenantDefinition(tenants)).build();
    }
}
