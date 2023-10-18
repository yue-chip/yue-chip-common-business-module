package com.yue.chip.upms.interfaces.vo.organizational;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.upms.definition.organizational.OrganizationalDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "组织架构树形结构")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(
        ignoreUnknown = true,
        value = { "updateDateTime", "createUserId", "updateUserId"}
)
public class OrganizationalTreeListVo extends OrganizationalDefinition {

    @Schema(description = "负责人姓名")
    private String leaderName;

    private List<OrganizationalTreeListVo> children;
}
