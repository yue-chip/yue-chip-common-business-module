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

    @Schema(description = "租户名称-简称")
    private String abbreviation;

    @Schema(description = "租户状态(code:"+State.code+",version:"+State.version+")")
    private State state;

    @Schema(description = "负责人")
    private String manager;

    @Schema(description = "负责人联系电话")
    private String phoneNumber;

    @Schema(description = "访问地址(xxx.xxx.com/120.102.25.45)-用户区分租户")
    private String domain;

    @Schema(description = "是否默认租户，默认租户不能删除(系统创建，不能手动添加)")
    private Boolean isDefault;

    @Schema(description = "租户编码")
    private Long tenantNumber;

    @Schema(description = "数字大屏名称")
    private String bigScreenName;
}
