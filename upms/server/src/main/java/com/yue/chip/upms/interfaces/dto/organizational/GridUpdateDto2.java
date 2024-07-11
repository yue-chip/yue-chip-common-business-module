package com.yue.chip.upms.interfaces.dto.organizational;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.upms.definition.organizational.GridDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2024-05-29
 */
@Data
@SuperBuilder
@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@JsonIgnoreProperties(ignoreUnknown = true,value = {"createUserId","updateUserId","createDateTime","updateDateTime","userId"})
public class GridUpdateDto2 extends GridDefinition {

    @Override
    @NotNull(message = "id不能为空")
    public Long getId() {
        return super.getId();
    }

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
    public Long getParentId() {
        return super.getParentId();
    }

    @Override
    @NotNull(message = "排序不能为空")
    public Integer getSort() {
        return super.getSort();
    }

    @Schema(description = "用户ids")
    @NotNull(message = "用户ids不能为空")
    private List<Long> userIds;


}
