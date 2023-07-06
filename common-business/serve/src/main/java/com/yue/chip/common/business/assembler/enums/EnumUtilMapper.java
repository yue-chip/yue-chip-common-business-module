package com.yue.chip.common.business.assembler.enums;

import com.yue.chip.common.business.domain.aggregates.enums.EnumUtil;
import com.yue.chip.common.business.infrastructure.po.enmus.EnumUtilPo;
import com.yue.chip.common.business.interfaces.dto.enuns.EnumUtilDto;
import org.hibernate.classic.Lifecycle;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/7/6 上午11:39
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EnumUtilMapper {
    EnumUtilMapper INSTANCE = Mappers.getMapper(EnumUtilMapper.class);

    public EnumUtil toEnumUtil(EnumUtilPo enumUtilPo);

    public EnumUtilPo toEnumUtilPo(EnumUtil enumUtil);

    public EnumUtilPo toEnumUtilPo(EnumUtilDto enumUtilDto);

    public List<EnumUtilPo> toEnumUtilPo(List<EnumUtilDto> enumUtilDtos);
}
