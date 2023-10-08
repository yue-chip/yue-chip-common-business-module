package com.yue.chip.upms.definition.organizational;

import com.yue.chip.core.BaseDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @description: 机构分组
 * @date 2023/10/7 下午1:39
 */
@Data
@Schema(description = "组织机构")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class OrganizationalGroupDefinition extends BaseDefinition {

    @Schema(description = "分组名称")
    private String name;
}
