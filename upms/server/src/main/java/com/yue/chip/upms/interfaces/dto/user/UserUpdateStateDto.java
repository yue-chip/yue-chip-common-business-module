package com.yue.chip.upms.interfaces.dto.user;

import com.yue.chip.core.common.enums.State;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jiacheng.liao on 2025/2/17
 */
@Data
@Builder
@Schema
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateStateDto {

    @Schema(description = "用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @Schema(description = "状态")
    @NotNull(message = "状态不能为空")
    private State state;

}
