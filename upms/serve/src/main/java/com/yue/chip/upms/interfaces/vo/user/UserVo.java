package com.yue.chip.upms.interfaces.vo.user;

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
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class UserVo extends UserDefinition {

    @Schema(description = "头像id")
    private Long profilePhoto;

    @Schema(description = "头像url")
    private String profilePhotoUrl;
}
