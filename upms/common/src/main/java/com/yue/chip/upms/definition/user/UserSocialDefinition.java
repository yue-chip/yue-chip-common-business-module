package com.yue.chip.upms.definition.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.core.BaseDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Schema(description = "用户的第三方登录信息")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true,value = {"updateDateTime","createUserId","updateUserId"})
public class UserSocialDefinition extends BaseDefinition {

    /** 用户ID */
    @Schema(description = "用户ID")
    private Long userId;

    /** 租户编号 */
    @Schema(description = "租户编号")
    private Long tenantNumber;

    /** 第三方绑定类型 */
    @Schema(description = "第三方绑定类型")
    private String type;

    /** 第三方UID */
    @Schema(description = "第三方UID")
    private String uid;

    /** 第三方账号 */
    @Schema(description = "第三方账号")
    private String acc;

}
