package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.upms.definition.user.UserWeiXinDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/7 下午6:25
 */
@Data
@EqualsAndHashCode(callSuper=false)
@SuperBuilder
@NoArgsConstructor

@Component
public class UserWeixin extends UserWeiXinDefinition {

}
