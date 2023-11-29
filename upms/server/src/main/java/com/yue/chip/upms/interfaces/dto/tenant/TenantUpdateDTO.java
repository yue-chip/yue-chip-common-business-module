package com.yue.chip.upms.interfaces.dto.tenant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.upms.definition.tenant.TenantDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/24 下午1:35
 */
@Data
@SuperBuilder
@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class TenantUpdateDTO extends TenantDefinition {

    @Override
    @NotNull(message = "id不能为空")
    public Long getId() {
        return super.getId();
    }

    @Override
    @NotBlank(message = "机构名称不能为空")
    public String getName() {
        return super.getName();
    }

    @Override
    @NotBlank(message = "负责人不能为空")
    public String getManager() {
        return super.getManager();
    }

    @Override
    @NotBlank(message = "联系电话不能为空")
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    @NotBlank(message = "租户名称简称不能为空")
    public String getAbbreviation() {
        return super.getAbbreviation();
    }

    @Override
    @NotBlank(message = "访问域名不能为空")
    public String getDomain() {
        return super.getDomain();
    }
}
