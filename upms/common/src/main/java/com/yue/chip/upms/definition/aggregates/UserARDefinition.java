package com.yue.chip.upms.definition.aggregates;

import com.yue.chip.upms.definition.user.UserDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:11
 * @description 用户聚合根
 */
@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
public class UserARDefinition extends UserDefinition {

    /**
     * 角色
     */
    private List<RoleARVODefinition> roles;
}
