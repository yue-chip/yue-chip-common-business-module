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
 * @date 2023/3/1 下午5:09
 */
@Data
@SuperBuilder
@Schema
public class ResourcesAUDto extends ResourcesDefinition {
    @Schema(description = "编码",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "编码不能为空",groups = {Validator.Insert.class,Validator.Update.class})
    @Override
    public String getCode() {
        return super.getCode();
    }

    @Schema(description = "名称",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "名称不能为空",groups = {Validator.Insert.class,Validator.Update.class})
    @Override
    public String getName() {
        return super.getName();
    }

    @Schema(description = "父节点id",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "父节点id不能为空",groups = {Validator.Insert.class,Validator.Update.class})
    @Override
    public Long getParentId() {
        return super.getParentId();
    }

    @Schema(description = "作用域",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "作用域不能为空",groups = {Validator.Insert.class,Validator.Update.class})
    @Override
    public Scope getScope() {
        return super.getScope();
    }

    @Schema(description = "类型",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "类型不能为空",groups = {Validator.Insert.class,Validator.Update.class})
    @Override
    public Type getType() {
        return super.getType();
    }
}
