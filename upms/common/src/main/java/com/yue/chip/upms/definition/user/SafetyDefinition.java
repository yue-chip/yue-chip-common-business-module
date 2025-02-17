package com.yue.chip.upms.definition.user;

import com.yue.chip.core.BaseDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author jiacheng.liao on 2024/12/11
 */
@Data
@Schema(description = "安全设置")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class SafetyDefinition extends BaseDefinition {

    @Schema(description = "超时无操作时间设置")
    private Long timeout;

    @Schema(description = "更新密码时间设置")
    private Long passwordTime;

    @Schema(description = "密码最小长度")
    private Long passwordLength;

    @Schema(description = "是否禁止连续使用同一字符")
    private Boolean sameChar;

    @Schema(description = "是否包含三种字符以上")
    private Boolean threeChar;

}
