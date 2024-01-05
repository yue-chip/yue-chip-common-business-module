package com.yue.chip.upms.interfaces.vo.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.upms.definition.role.RoleDefinition;
//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/3/3 下午2:49
 */
@Data
//@Schema(description = "角色")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class RoleVo extends RoleDefinition {
}
