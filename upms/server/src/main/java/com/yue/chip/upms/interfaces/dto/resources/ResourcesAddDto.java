package com.yue.chip.upms.interfaces.dto.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.core.persistence.Validator;
import com.yue.chip.upms.definition.resources.ResourcesDefinition;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.enums.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/3/1 下午4:38
 */
@Data
@SuperBuilder
@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@JsonIgnoreProperties(ignoreUnknown = true,value = {"id","createUserId","updateUserId","createDateTime","updateDateTime"})
public class ResourcesAddDto extends ResourcesAUDto {


}
