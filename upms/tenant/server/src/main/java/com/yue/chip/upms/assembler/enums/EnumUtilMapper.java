package com.yue.chip.upms.assembler.enums;

import com.yue.chip.core.common.enums.EnumPersistenceBean;
import com.yue.chip.core.common.enums.EnumUtilDefinition;
import com.yue.chip.upms.domain.aggregates.enums.EnumUtil;
import com.yue.chip.upms.infrastructure.po.enmus.EnumUtilPo;
import com.yue.chip.upms.interfaces.vo.enums.EnumUtilVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Mr.Liu
 * @date 2023/7/6 上午11:39
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE,implementationName = "upmsEnumUtilMapper")
public interface EnumUtilMapper {
    EnumUtilMapper INSTANCE = Mappers.getMapper(EnumUtilMapper.class);

    public EnumUtilDefinition toEnumUtilDefinition(EnumUtilPo enumUtilPo);

    public EnumUtilDefinition toEnumUtilDefinition(EnumUtil enumUtil);

    public EnumUtilVo toEnumUtilVo(EnumUtil enumUtil);

    public EnumUtilPo toEnumUtilPo(EnumUtil enumUtil);

    public EnumUtilPo toEnumUtilPo(EnumPersistenceBean enumPersistenceBean);

    public EnumUtil toEnumUtil(EnumUtilPo enumUtilPo);
}
