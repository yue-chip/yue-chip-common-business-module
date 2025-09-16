package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.upms.definition.user.UserWeiXinDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/7 下午6:25
 */
@Data
@EqualsAndHashCode(callSuper=false)
@SuperBuilder
@NoArgsConstructor
public class UserWeixin extends UserWeiXinDefinition {

    public int hashCode() {
        return 1;
    }

}
