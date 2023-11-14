package com.yue.chip.upms.interfaces.dto.organizational;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.Validator;
import com.yue.chip.upms.definition.organizational.OrganizationalDefinition;
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
 * @date 2023/10/8 下午4:05
 */
@Data
@SuperBuilder
@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class OrganizationalAUDto extends OrganizationalDefinition {

    @Override
    @Schema(description = "机构名称",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "机构名称不能为空",groups = {Validator.Insert.class,Validator.Update.class})
    public String getName() {
        return super.getName();
    }

    @Schema(description = "紧急联系电话",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "紧急联系电话不能为空",groups = {Validator.Insert.class,Validator.Update.class})
    @Override
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }
}
