package com.yue.chip.common.business.definition.enums;

import com.yue.chip.core.BaseDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author mr.liu
 * @Description: 保存所有微服务的枚举值，用于前端生成select下拉框
 * @date 2020/9/16下午3:52
 */
@Data
@Schema(description = "保存所有微服务的枚举值，用于前端生成select下拉框")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class EnumUtilDefinition extends BaseDefinition {
    private static final long serialVersionUID = -2124525197256620973L;

    @Schema(description = "枚举code值")
    private String code;

    @Schema(description = "枚举version值")
    private String version;

    @Schema(description = "枚举value值")
    private String value;

}
