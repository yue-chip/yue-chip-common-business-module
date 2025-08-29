package com.yue.chip.upms.assembler.organizational;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2023-11-23
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrganizationalUserMapper {
    OrganizationalUserMapper INSTANCE = Mappers.getMapper(OrganizationalUserMapper.class);
}
