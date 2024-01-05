package com.yue.chip.upms.definition.resources;

import com.yue.chip.core.BaseDefinition;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.enums.Type;
//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:02
 * @description Resources 字段的定义 避免在聚合实体entity，dto，vo，po……等bean 进行重复定义
 */
@Data
//@Schema(description = "资源")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class ResourcesDefinition extends BaseDefinition {

    //@Schema(description = "父节点ID")
    private Long parentId;

    //@Schema(description = "资源编码")
    private String code;

    //@Schema(description = "资源名称")
    private String name;

    //@Schema(description = "作用域-(code:"+Scope.code+",version:"+Scope.version+")")
    @Builder.Default
    private Scope scope = Scope.CONSOLE;

    //@Schema(description = "类型-(code:"+Type.code+",version:"+Type.version+")")
    @Builder.Default
    private Type type = Type.CATALOG;

    //@Schema(description = "排序")
    private Integer sort;

    //@Schema(description = "状态-(code:"+State.code+",version:"+State.version+")")
    @Builder.Default
    private State state = State.NORMAL;

    //@Schema(description = "是否默认资源,默认资源不能删除")
    @Builder.Default
    private Boolean isDefault = false;

    //@Schema(description = "url")
    private String url;

    //@Schema(description = "备注")
    private String remark;

}
