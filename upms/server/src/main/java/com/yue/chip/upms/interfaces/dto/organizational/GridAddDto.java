package com.yue.chip.upms.interfaces.dto.organizational;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.upms.definition.organizational.GridDefinition;
//import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/21 下午2:42
 */
@Data
@SuperBuilder
//@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@JsonIgnoreProperties(ignoreUnknown = true,value = {"id","createUserId","updateUserId","createDateTime","updateDateTime"})
public class GridAddDto extends GridDefinition {

    @Override
    @NotBlank(message = "网格名称不能为空")
    public String getName() {
        return super.getName();
    }

    @Override
    @NotNull(message = "机构id不能为空")
    public Long getOrganizationalId() {
        return super.getOrganizationalId();
    }

    @Override
    @NotNull(message = "用户id不能为空")
    public Long getUserId() {
        return super.getUserId();
    }
}
