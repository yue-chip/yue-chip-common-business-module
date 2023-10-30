package com.yue.chip.upms.interfaces.dto.user;

import com.yue.chip.core.persistence.Validator;
import com.yue.chip.upms.definition.user.UserDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/4/20 下午3:19
 */
@Data
@Builder
@Schema
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatePasswordDto{

    @Schema(description = "密码(md5编码)")
    @NotBlank(message = "密码不能为空")
    private String password;

}
