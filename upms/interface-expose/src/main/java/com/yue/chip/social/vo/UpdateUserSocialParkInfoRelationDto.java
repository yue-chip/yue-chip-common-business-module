package com.yue.chip.social.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rjp
 * @description:
 * @date 2025-11-27
 */
@Data
@NoArgsConstructor
public class UpdateUserSocialParkInfoRelationDto {
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @NotNull(message = "所属园区不能为空")
    private Long parkInfoId;

    @NotNull(message = "是否绑定不能为空")
    private Boolean isBind;

    private String userName;

    private String phoneNumber;
}
