package com.yue.chip.social.vo;

import com.yue.chip.upms.definition.user.UserSocialDefinition;
import com.yue.chip.upms.definition.user.UserWeiXinDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class UserSocialExposeVo extends UserSocialDefinition {
}
