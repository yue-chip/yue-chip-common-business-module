package com.yue.chip.upms.interfaces.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户分页DTO
 *
 * @author ZhangYuanLin
 */
@Data
@Builder
@Schema
@NoArgsConstructor
@AllArgsConstructor
public class UserPageDto {

    @Schema(description = "姓名或账号")
    private String nameOrUsername;

    @Schema(description = "联系电话")
    private String phoneNumber;

}
