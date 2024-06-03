package com.yue.chip.grid.vo;

import com.yue.chip.upms.definition.organizational.GridDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2024-06-03
 */
@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class GridVo extends GridDefinition {

    /**
     * 网格管理员
     */
    private List<UserVo> user;

    private List<GridVo> children;


}
