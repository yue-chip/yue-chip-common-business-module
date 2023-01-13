package com.yue.chip.upms.definition.role;

import com.yue.chip.core.BaseDefinition;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.upms.enums.Scope;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午1:56
 * @description RoleDefinition
 */
@Data
@Schema(description = "角色")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class RoleDefinition extends BaseDefinition {

    @Schema(description = "角色编码")
    private String code;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "作用域")
    private Scope scope;

    @Schema(description = "是否默认角色（0：否，1：是）默认角色不能删除")
    private Boolean isDefault;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "状态")
    private State state;

}
