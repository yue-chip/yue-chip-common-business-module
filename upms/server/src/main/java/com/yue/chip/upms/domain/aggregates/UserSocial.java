package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.upms.definition.user.UserSocialDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

/**
 * @author zak
 */
@Data
@EqualsAndHashCode(callSuper=false)
@SuperBuilder
@NoArgsConstructor
@Component
public class UserSocial extends UserSocialDefinition {

    public int hashCode() {
        return 1;
    }

}
