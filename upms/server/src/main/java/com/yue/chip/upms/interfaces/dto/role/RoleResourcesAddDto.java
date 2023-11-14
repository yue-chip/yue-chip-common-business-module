package com.yue.chip.upms.interfaces.dto.role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * @author Mr.Liu
 * @date 2023/3/4 上午11:08
 */
@Data
@Schema
public class RoleResourcesAddDto {

    @Schema(description = "角色id",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "角色id不能为空")
    private Long roleId;

    @Schema(description = "资源权限id",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long[] resourcesIds;
}
