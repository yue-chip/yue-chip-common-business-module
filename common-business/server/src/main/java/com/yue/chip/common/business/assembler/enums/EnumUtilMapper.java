package com.yue.chip.common.business.assembler.enums;

import com.yue.chip.common.business.domain.aggregates.enums.EnumUtil;
import com.yue.chip.common.business.interfaces.vo.enums.EnumUtilVo;
import com.yue.chip.core.common.enums.EnumUtilDefinition;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Mr.Liu
 * @date 2023/7/6 上午11:39
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EnumUtilMapper {
    EnumUtilMapper INSTANCE = Mappers.getMapper(EnumUtilMapper.class);

    public EnumUtilVo toEnumUtilVo(EnumUtil enumUtil);

    public EnumUtilVo toEnumUtilVo(EnumUtilDefinition enumUtilDefinition);

}
