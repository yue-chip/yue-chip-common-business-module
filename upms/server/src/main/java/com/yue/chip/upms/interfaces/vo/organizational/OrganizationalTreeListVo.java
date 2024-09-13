package com.yue.chip.upms.interfaces.vo.organizational;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.upms.definition.organizational.OrganizationalDefinition;
import com.yue.chip.utils.Sm4Api;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/9 上午9:53
 */
@Data
//@Schema(description = "组织架构树形结构")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(
        ignoreUnknown = true,
        value = { "updateDateTime", "createUserId", "updateUserId"}
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizationalTreeListVo extends OrganizationalDefinition {

    //@Schema(description = "负责人姓名")
    private String leaderName;

    //@Schema(description = "子节点")
    private List<OrganizationalTreeListVo> children;

    //@Schema(description = "用于列表a-switch组建展示")
    private Boolean stateTmp;

    public Boolean getStateTmp() {
        return Objects.equals(getState(), State.NORMAL);
    }

    public String getLeaderName() {
        if (StringUtils.hasText(this.leaderName)) {
            return new Sm4Api().generalDataDec( this.leaderName);
        }
        return this.leaderName;
    }
}
