package com.yue.chip.upms.definition.aggregates;

import com.yue.chip.upms.definition.resources.ResourcesDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:17
 * @description 资源值对象
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
public class ResourcesVODefinition extends ResourcesDefinition {
}
