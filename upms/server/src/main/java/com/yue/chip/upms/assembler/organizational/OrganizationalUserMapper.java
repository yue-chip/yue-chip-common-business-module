package com.yue.chip.upms.assembler.organizational;

import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalUserPo;
import com.yue.chip.upms.vo.OrganizationalUserExposeVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2023-11-23
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrganizationalUserMapper {
    OrganizationalUserMapper INSTANCE = Mappers.getMapper(OrganizationalUserMapper.class);
    List<OrganizationalUserExposeVo> toListOrganizationalUserExposeVo(List<OrganizationalUserPo> organizationalUserPoList);
}
