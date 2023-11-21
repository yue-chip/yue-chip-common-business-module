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
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/21 下午2:50
 */
@Data
@Schema(description = "网格")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class GridVo extends GridDefinition {

    @Schema(description = "网格管理员")
    private List<UserVo> users;

}
