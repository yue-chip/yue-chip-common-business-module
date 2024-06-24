package com.yue.chip.upms.definition.organizational;

import com.yue.chip.core.BaseDefinition;
//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2024-05-29
 */
@Data
//@Schema(description = "网格-网格员")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class GridUserDefinition extends BaseDefinition {

//    @Schema(description = "网格员id")
    private Long userId;

//    @Schema(description = "网格id")
    private Long gridId;

}
