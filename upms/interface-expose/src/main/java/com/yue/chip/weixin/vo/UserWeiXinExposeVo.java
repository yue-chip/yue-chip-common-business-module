package com.yue.chip.weixin.vo;

import com.yue.chip.upms.definition.user.UserWeiXinDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class UserWeiXinExposeVo extends UserWeiXinDefinition {
}
