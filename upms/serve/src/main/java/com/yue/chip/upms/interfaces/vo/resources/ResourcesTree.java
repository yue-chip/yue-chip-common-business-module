package com.yue.chip.upms.interfaces.vo.resources;

import com.yue.chip.upms.definition.aggregates.ResourcesVODefinition;
import com.yue.chip.upms.definition.resources.ResourcesDefinition;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.infrastructure.po.resources.ResourcesPo;
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
public class ResourcesTree extends ResourcesDefinition {

    private List<ResourcesTree> children;
}
