package com.yue.chip.upms.interfaces.vo.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.upms.definition.user.SafetyDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author jiacheng.liao on 2024/12/11
 */
@Data
@Schema(description = "租户")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true, value = {"id","updateDateTime", "createUserId", "updateUserId"})
public class SafetyVo extends SafetyDefinition {
}
