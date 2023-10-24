package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.annotation.YueChipDDDEntity;
import com.yue.chip.upms.definition.tenant.TenantDefinition;
import com.yue.chip.upms.domain.repository.tenant.TenantRepository;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/24 上午10:51
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@YueChipDDDEntity
public class Tenant extends TenantDefinition {

    @Resource
    private TenantRepository tenantRepository;

    public Boolean checkNameIsExist() {
        Assert.hasText(getName(),"名称不能为空");
        Optional<Tenant> optional = tenantRepository.findByName(getName());
        if (optional.isPresent()) {
            if (Objects.nonNull(getId()) && optional.get().getId().equals(getId())){
                return false;
            }
            return true;
        }
        return false;
    }
}
