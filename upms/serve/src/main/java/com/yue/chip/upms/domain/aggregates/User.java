package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.upms.definition.aggregates.UserARDefinition;
import com.yue.chip.upms.domain.repository.user.UserRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:11
 * @description 用户聚合根
 */
@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class User extends UserARDefinition {

    private UserRepository userRepository;

}
