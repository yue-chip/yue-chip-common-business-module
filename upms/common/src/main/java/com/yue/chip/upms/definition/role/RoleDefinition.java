package com.yue.chip.upms.definition.role;

import com.yue.chip.core.BaseDefinition;
import com.yue.chip.core.common.enums.State;
//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午1:56
 * @description Role字段的定义 避免在聚合实体entity，dto，vo，po……等bean 进行重复定义
 */
@Data
//@Schema(description = "角色")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class RoleDefinition extends BaseDefinition {

    //@Schema(description = "角色编码")
    private String code;

    //@Schema(description = "角色名称")
    private String name;

    //@Schema(description = "是否默认角色（0：否，1：是）默认角色不能删除")
    @Builder.Default
    private Boolean isDefault = false;

    //@Schema(description = "备注")
    private String remark;

    //@Schema(description = "状态-(code:"+State.code+",version:"+State.version+")")
    @Builder.Default
    private State state = State.NORMAL;

}
