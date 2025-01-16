package com.yue.chip.upms.interfaces.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * @author jiacheng.liao on 2024/12/11
 */
@Data
@Schema
public class UseRoleLDeleteDto {

    @Schema(description = "用户id数组")
    private List<Long> userIds;

    @Schema(description = "角色id")
    @NotNull(message = "角色id不能为空")
    private Long roleId;

}
