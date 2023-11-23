package com.yue.chip.upms.vo;

import com.yue.chip.upms.definition.organizational.OrganizationalUserDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2023-11-23
 */
@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class OrganizationalUserExposeVo extends OrganizationalUserDefinition {
}
