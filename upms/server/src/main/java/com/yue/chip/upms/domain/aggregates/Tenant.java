package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.annotation.YueChipDDDEntity;
import com.yue.chip.upms.definition.tenant.TenantDefinition;
import com.yue.chip.upms.domain.repository.tenant.TenantRepository;
import com.yue.chip.utils.AssertUtil;
import javax.annotation.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
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
    private static TenantRepository tenantRepository;


    public Boolean checkNameIsExist() {
        Assert.hasText(getName(),"名称不能为空");
        Optional<Tenant> optional = tenantRepository.findTenantByName(getName());
        if (optional.isPresent()) {
            if (Objects.nonNull(getId()) && optional.get().getId().equals(getId())){
                return false;
            }
            return true;
        }
        return false;
    }

    public Boolean checkDomainIsExist(Long updateId) {
        AssertUtil.hasText(getRequestDomain(),"登录域不能为空");
        List<Tenant> list = tenantRepository.findAll();
        for (Tenant tenant : list) {
            String domain1 = tenant.getRequestDomain();
            if (StringUtils.hasText(domain1)) {
                String[] strs = domain1.split(",");
                for (String str : strs) {
                    if (Objects.equals(getRequestDomain(),str)) {
                        if ((Objects.nonNull(getId()) && !Objects.equals(getId(),updateId)) || Objects.isNull(getId())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
