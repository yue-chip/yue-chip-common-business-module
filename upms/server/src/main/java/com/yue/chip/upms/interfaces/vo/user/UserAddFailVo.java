package com.yue.chip.upms.interfaces.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author jiacheng.liao on 2024/12/13
 */
@Data
@SuperBuilder
@Schema
@NoArgsConstructor
public class UserAddFailVo {

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "失败原因")
    private String cause;
}
