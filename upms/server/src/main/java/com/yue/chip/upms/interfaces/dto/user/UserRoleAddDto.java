package com.yue.chip.upms.interfaces.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jdk.management.jfr.RecordingInfo;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/3/8 下午3:04
 */
@Data
@Schema
public class UserRoleAddDto {

    @Schema(description = "角色id",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "角色id不能为空")
    private Long roleId;


    @Schema(description = "用户id-全量，先删后增")
    private Long[] userIds;
}
