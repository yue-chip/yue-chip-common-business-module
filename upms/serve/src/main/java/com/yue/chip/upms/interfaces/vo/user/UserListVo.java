package com.yue.chip.upms.interfaces.vo.user;

import com.yue.chip.upms.definition.user.UserDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/3/8 下午1:49
 */
@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class UserListVo extends UserDefinition {
}
