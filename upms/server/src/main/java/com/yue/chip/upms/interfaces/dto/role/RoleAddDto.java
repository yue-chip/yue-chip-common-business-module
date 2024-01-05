package com.yue.chip.upms.interfaces.dto.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/3/3 下午3:07
 */
@Data
@SuperBuilder
//@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@JsonIgnoreProperties(ignoreUnknown = true,value = {"id","createUserId","updateUserId","createDateTime","updateDateTime"})
public class RoleAddDto extends RoleAUDto{
}
