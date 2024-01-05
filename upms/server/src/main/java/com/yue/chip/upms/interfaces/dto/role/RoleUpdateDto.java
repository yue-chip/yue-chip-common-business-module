package com.yue.chip.upms.interfaces.dto.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.core.persistence.Validator;
//import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/3/3 下午3:08
 */
@Data
@SuperBuilder
//@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@JsonIgnoreProperties(ignoreUnknown = true,value = {"createUserId","updateUserId","createDateTime","updateDateTime"})
public class RoleUpdateDto extends RoleAUDto{

    //@Schema(description = "id",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    @Override
    public Long getId() {
        return super.getId();
    }

//    //@Schema(description = "版本号" ,requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotNull(message="版本号不能为空")
//    @Override
//    public Long getVersion() {
//        return super.getVersion();
//    }
}
