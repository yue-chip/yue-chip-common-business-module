package com.yue.chip.tenant.vo;

import com.yue.chip.upms.definition.tenant.TenantDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/22 下午2:05
 */
@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class TenantExposeVo extends TenantDefinition {

}
