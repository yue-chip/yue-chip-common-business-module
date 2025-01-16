package com.yue.chip.upms.interfaces.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author jiacheng.liao on 2024/12/11
 */
@Data
@Schema
public class UserListDto {

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "账号")
    private String username;

    @Schema(description = "电话号码")
    private String phoneNumber;

}
