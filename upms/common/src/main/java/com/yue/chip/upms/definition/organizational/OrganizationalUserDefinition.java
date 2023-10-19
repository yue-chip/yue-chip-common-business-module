package com.yue.chip.upms.definition.organizational;

import com.yue.chip.core.BaseDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @description: 用户与机构关联关系
 * @date 2023/10/7 下午1:44
 */
@Data
@Schema(description = "用户与机构关联关系")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class OrganizationalUserDefinition extends BaseDefinition {

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "机构id")
    private Long organizationalId;
}
