package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.upms.definition.aggregates.RoleARVODefinition;
import com.yue.chip.upms.definition.role.RoleDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:13
 * @description 角色值对象/聚合根（角色-资源，角色就是聚合根）
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class RoleARVO extends RoleARVODefinition {

}
