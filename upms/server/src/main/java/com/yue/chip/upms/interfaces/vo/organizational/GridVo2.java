package com.yue.chip.upms.interfaces.vo.organizational;

import com.yue.chip.upms.definition.organizational.GridDefinition;
import com.yue.chip.upms.interfaces.vo.user.UserVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2024-05-29
 */
@Data
@Schema(description = "网格")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class GridVo2 extends GridDefinition {

    /**
     * 网格管理员
     */
    private List<UserVo> user;

    @Schema(description = "子网格")
    private List<GridVo2> children;

}
