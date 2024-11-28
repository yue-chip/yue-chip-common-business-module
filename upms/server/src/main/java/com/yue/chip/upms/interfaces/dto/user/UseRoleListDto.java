package com.yue.chip.upms.interfaces.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author jiacheng.liao on 2024/12/10
 */
@Data
@Schema
public class UseRoleListDto {

    @Schema(description = "角色id")
    @NotNull(message = "角色id不能为空")
    private Long roleId;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "电话号码")
    private String phone;

}
