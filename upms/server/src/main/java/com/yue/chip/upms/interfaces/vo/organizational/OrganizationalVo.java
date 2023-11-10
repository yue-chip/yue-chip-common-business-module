package com.yue.chip.upms.interfaces.vo.organizational;

import com.yue.chip.upms.definition.organizational.OrganizationalDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/9 上午9:53
 */
@Data
@Schema(description = "组织架构")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class OrganizationalVo extends OrganizationalDefinition {
}
