package com.yue.chip.upms.interfaces.dto.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.upms.definition.role.RoleDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/3/3 下午3:04
 */
@Data
@SuperBuilder
@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class RoleAUDto extends RoleDefinition {

    @Schema(description = "编码",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "编码不能为空")
    @Override
    public String getCode() {
        return super.getCode();
    }

    @Schema(description = "名称",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "名称不能为空")
    @Override
    public String getName() {
        return super.getName();
    }
}
