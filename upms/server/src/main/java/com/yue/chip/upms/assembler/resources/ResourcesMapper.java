package com.yue.chip.upms.assembler.resources;

import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.infrastructure.po.resources.ResourcesPo;
import com.yue.chip.upms.interfaces.dto.resources.ResourcesAUDto;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTreeVo;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTreeListVo;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/2/28 上午10:29
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResourcesMapper {

    ResourcesMapper INSTANCE = Mappers.getMapper(ResourcesMapper.class);

    public ResourcesTreeListVo toResourcesTreeListVo(ResourcesPo resourcesPo);

    public List<ResourcesTreeVo> toResourcesTreeVo(List<ResourcesTreeListVo> list);

    @Mappings({@Mapping(source = "id", target = "key"),
            @Mapping(source = "name", target = "title")
    })
    public ResourcesTreeVo toResourcesTreeVo(ResourcesTreeListVo resourcesTreeListVo);

    public Resources toResources(ResourcesPo resourcesPo);

    public List<Resources> toResourcesList(List<ResourcesPo> list);

    public ResourcesPo toResourcesPo(ResourcesAUDto resources);

    public ResourcesVo toResourcesVo(Resources resources);
}
