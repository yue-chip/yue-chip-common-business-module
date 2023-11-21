package com.yue.chip.upms.vo;

import com.yue.chip.upms.definition.organizational.GridDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/21 下午3:24
 */
@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class GridExposeVo extends GridDefinition {

    /**
     * 网格管理员
     */
    private UserExposeVo user;
}
