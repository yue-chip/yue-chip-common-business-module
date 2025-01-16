package com.yue.chip.upms.interfaces.dto.user;

import com.yue.chip.upms.definition.user.SafetyDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author jiacheng.liao on 2024/12/11
 */
@Data
@SuperBuilder
@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class SafetyUpdateDto extends SafetyDefinition {

    @Override
    @NotNull(message = "超时无操作时间设置不能为空")
    public Long getTimeout() {
        return super.getTimeout();
    }

    @Override
    @NotNull(message = "更新密码时间设置不能为空")
    public Long getPasswordTime() {
        return super.getPasswordTime();
    }
}
