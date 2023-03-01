package com.yue.chip.upms.interfaces.dto.resources;

import com.yue.chip.core.persistence.Validator;
import com.yue.chip.upms.definition.resources.ResourcesDefinition;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.enums.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/3/1 下午4:38
 */
@Data
@SuperBuilder
@Schema
public class ResourcesUpdateDto extends ResourcesAUDto {

    @Schema(description = "id",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空",groups = {Validator.Update.class})
    @Override
    public Long getId() {
        return super.getId();
    }
}
