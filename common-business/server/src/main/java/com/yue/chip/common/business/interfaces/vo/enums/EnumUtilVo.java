package com.yue.chip.common.business.interfaces.vo.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/7/6 上午11:46
 */
@Data
@Schema(description = "枚举")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class EnumUtilVo extends com.yue.chip.core.common.enums.EnumUtilDefinition {
}
