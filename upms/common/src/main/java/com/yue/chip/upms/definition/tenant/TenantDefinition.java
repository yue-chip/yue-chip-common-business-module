package com.yue.chip.upms.definition.tenant;

import com.yue.chip.core.BaseDefinition;
import com.yue.chip.core.common.enums.State;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/23 上午10:47
 */
@Data
@Schema(description = "租户")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class TenantDefinition extends BaseDefinition {

    @Schema(description = "租户名称")
    private String name;

    @Schema(description = "租户状态(code:"+State.code+",version:"+State.version+")")
    private State state;

    @Schema(description = "负责人")
    private String manager;

    @Schema(description = "负责人联系电话")
    private String phoneNumber;
}
