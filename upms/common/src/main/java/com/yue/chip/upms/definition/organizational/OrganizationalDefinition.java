package com.yue.chip.upms.definition.organizational;

import com.yue.chip.core.BaseDefinition;
import com.yue.chip.core.common.enums.State;
//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @description: 组织机构
 * @date 2023/10/7 下午1:36
 */
@Data
//@Schema(description = "组织机构")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class OrganizationalDefinition extends BaseDefinition {

    //@Schema(description = "机构名称")
    private String name;

    //@Schema(description = "上级节点")
    @Builder.Default
    private Long parentId = 0L;

    //@Schema(description = "机构分组")
    private Long organizationalGroupId;

    //@Schema(description = "排序")
    private Integer sort;

    //@Schema(description = "负责人")
    private Long leaderId;

    //@Schema(description = "状态")
    private State state;

    //@Schema(description = "紧急联系电话")
    private String phoneNumber;

}
