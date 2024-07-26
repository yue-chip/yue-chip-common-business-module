package com.yue.chip.upms.interfaces.vo.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.upms.definition.user.UserDefinition;
//import io.swagger.v3.oas.annotations.media.Schema;
import com.yue.chip.upms.domain.aggregates.Organizational;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/3/8 下午1:49
 */
@Data
//@Schema(description = "用户")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true,value = {"updateDateTime","password","accountNonExpired","accountNonLocked","credentialsNonExpired","enabled","createUserId","updateUserId"})
public class UserVo extends UserDefinition {

    //@Schema(description = "组织机构名称")
    private String organizationalName;

    //@Schema(description = "组织机构id")
    private Long organizationalId;

    //@Schema(description = "租户名称")
    private List<Organizational> organizationalList;

//    @Schema(description = "租户名称")
    private String tenantName;

    //@Schema(description = "租户简称")
    private String tenantAbbreviation;

    //@Schema(description = "租户编码")
    private Long tenantNumber;

    //@Schema(description = "数字大屏名称")
    private String bigScreenName;
}
