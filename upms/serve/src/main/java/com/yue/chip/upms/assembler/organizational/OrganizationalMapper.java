package com.yue.chip.upms.assembler.organizational;

import com.yue.chip.upms.domain.aggregates.Organizational;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalPo;
import com.yue.chip.upms.interfaces.dto.organizational.OrganizationalAddDto;
import com.yue.chip.upms.interfaces.dto.organizational.OrganizationalUpdateDto;
import com.yue.chip.upms.interfaces.vo.organizational.OrganizationalTreeListVo;
import com.yue.chip.upms.interfaces.vo.organizational.OrganizationalTreeSelectVo;
import com.yue.chip.upms.interfaces.vo.organizational.OrganizationalVo;
import com.yue.chip.upms.vo.OrganizationalExposeVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/8 下午3:14
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrganizationalMapper {

    OrganizationalMapper INSTANCE = Mappers.getMapper(OrganizationalMapper.class);

    public Organizational toOrganizational(OrganizationalPo organizationalPo);

    public OrganizationalPo toOrganizationalPo(OrganizationalAddDto organizationalAddDto);

    public OrganizationalPo toOrganizationalPo(OrganizationalUpdateDto organizationalUpdateDto);

    public List<OrganizationalTreeListVo> toOrganizationalTreeListVo(List<OrganizationalPo> list);

    @Mappings({@Mapping(target = "label",source = "name"),
            @Mapping(target = "title",source = "name"),
            @Mapping(target = "pId",source = "parentId"),
            @Mapping(target = "value",source = "id")})
    public List<OrganizationalTreeSelectVo> toOrganizationalTreeSelectVo(List<OrganizationalTreeListVo> list);

    @Mappings({@Mapping(target = "label",source = "name"),
            @Mapping(target = "title",source = "name"),
            @Mapping(target = "pId",source = "parentId"),
            @Mapping(target = "value",source = "id")})
    public OrganizationalTreeSelectVo toOrganizationalTreeSelectVo(OrganizationalTreeListVo organizationalTreeListVo);

    public OrganizationalTreeListVo toOrganizationalTreeListVo(OrganizationalPo organizationalPo);

    public OrganizationalVo toOrganizationalVo(Organizational organizational);

    public List<OrganizationalExposeVo> toOrganizationalExposeVoList(List<Organizational> organizationalList);

    public OrganizationalExposeVo toOrganizationalExposVo(Organizational organizational);


}
