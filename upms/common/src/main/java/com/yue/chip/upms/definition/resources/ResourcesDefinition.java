package com.yue.chip.upms.definition.resources;

import com.yue.chip.core.BaseDefinition;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.enums.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:02
 * @description ResourcesDefinition
 */
@Data
@Schema(description = "资源")
@MappedSuperclass
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
public class ResourcesDefinition extends BaseDefinition {

    @Schema(description = "父节点ID")
    private Long parentId;

    @Schema(description = "资源编码")
    private String code;

    @Schema(description = "资源名称")
    private String name;

    @Schema(description = "作用域")
    private Scope scope;

    @Schema(description = "类型")
    private Type type;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态")
    private State state;

    @Schema(description = "是否默认资源,默认资源不能删除")
    private Boolean isDefault;

    @Schema(description = "url")
    private String url;

    @Schema(description = "备注")
    private String remark;

    public ResourcesDefinition() {
    }
}
