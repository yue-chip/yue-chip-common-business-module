package com.yue.chip.upms.interfaces.vo.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.upms.definition.user.UserDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/3/8 下午1:49
 */
@Data
@Schema(description = "用户")
@SuperBuilder
@NoArgsConstructor
public class UserSelectVo {

    @Schema(description = "用户id")
    private String value;

    @Schema(description = "用户姓名")
    private String label;
    
}
