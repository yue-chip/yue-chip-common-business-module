package com.yue.chip.upms.interfaces.vo.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.upms.definition.resources.ResourcesDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/2/28 下午2:31
 */
@Data
@Schema(description = "资源")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourcesTreeList extends ResourcesDefinition {

    private List<ResourcesTreeList> children;
}
