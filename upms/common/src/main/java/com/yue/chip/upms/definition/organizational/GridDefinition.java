package com.yue.chip.upms.definition.organizational;

import com.yue.chip.core.BaseDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/21 上午11:28
 */
@Data
@Schema(description = "网格")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class GridDefinition extends BaseDefinition {

    @Schema(description = "网格名称")
    private String name;

    @Schema(description = "机构Id")
    private Long organizationalId;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "父节点id")
    private Long parentId;

    @Schema(description = "排序")
    private Integer sort;

}
