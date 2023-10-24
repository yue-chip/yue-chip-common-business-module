package com.yue.chip.upms.interfaces.vo.tenant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.upms.definition.tenant.TenantDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/24 上午11:35
 */
@Data
@Schema(description = "租户")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(
        ignoreUnknown = true,
        value = { "updateDateTime", "createUserId", "updateUserId"}
)
public class TenantVo extends TenantDefinition {
}
