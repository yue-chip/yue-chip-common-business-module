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
        value = {"name","parentId","organizationalGroupId","sort","leaderId","state","phoneNumber","createDateTime", "updateDateTime", "createUserId", "updateUserId"}
)
public class OrganizationalTreeSelectVo extends OrganizationalDefinition {

    @Schema(description = "机构名称")
    private String label;

    @Schema(description = "机构名称")
    private String title;

    @Schema(description = "父节点id")
    private Long pId;

    @Schema(description = "机构id")
    private Long value;


    private List<OrganizationalTreeSelectVo> children;
}
