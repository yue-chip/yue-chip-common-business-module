package com.yue.chip.common.business.domain.aggregates.enums;

import com.yue.chip.annotation.YueChipDDDEntity;
import com.yue.chip.common.business.definition.enums.EnumUtilDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/7/6 上午11:34
 */
@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
@YueChipDDDEntity
public class EnumUtil extends EnumUtilDefinition {
}
