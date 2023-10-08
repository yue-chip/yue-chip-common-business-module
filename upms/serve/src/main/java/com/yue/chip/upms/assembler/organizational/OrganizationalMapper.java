package com.yue.chip.upms.assembler.organizational;

import com.yue.chip.upms.assembler.user.UserMapper;
import com.yue.chip.upms.domain.aggregates.Organizational;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalPo;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalUserPo;
import com.yue.chip.upms.interfaces.dto.organizational.OrganizationalAddDto;
import com.yue.chip.upms.interfaces.dto.organizational.OrganizationalUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

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
}
