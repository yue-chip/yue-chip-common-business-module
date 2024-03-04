package com.yue.chip.upms.interfaces.vo.resources;

import com.yue.chip.upms.definition.resources.ResourcesDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
public class ResourcesTreeListVo extends ResourcesDefinition {

    private String iconUrl;

    private List<ResourcesTreeListVo> children;
}
