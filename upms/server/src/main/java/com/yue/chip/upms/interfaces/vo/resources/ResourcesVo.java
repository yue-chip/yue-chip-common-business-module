package com.yue.chip.upms.interfaces.vo.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.upms.definition.resources.ResourcesDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/4/6 上午10:17
 */
@Data
@Schema(description = "资源")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class ResourcesVo extends ResourcesDefinition {
}
