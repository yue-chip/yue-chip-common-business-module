package com.yue.chip.upms.infrastructure.assembler.resources;

import com.yue.chip.upms.definition.aggregates.ResourcesVODefinition;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.infrastructure.po.resources.ResourcesPo;
import com.yue.chip.upms.interfaces.dto.resources.ResourcesAUDto;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTree;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTreeList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/2/28 上午10:29
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResourcesMapper {

    public List<Resources> toListResources(List<ResourcesVODefinition> list);

    public ResourcesTreeList toResourcesTreeList(ResourcesPo resourcesPo);

    public List<ResourcesTree> toResourcesTree(List<ResourcesTreeList> list);

    @Mappings({@Mapping(source = "id", target = "key"),
            @Mapping(source = "name", target = "title")
    })
    public ResourcesTree toResourcesTree(ResourcesTreeList resourcesTreeList);

    public Resources toResources(ResourcesPo resourcesPo);

    public ResourcesPo toResourcesPo(ResourcesAUDto resources);
}
