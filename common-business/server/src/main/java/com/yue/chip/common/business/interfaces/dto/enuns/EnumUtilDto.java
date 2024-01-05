package com.yue.chip.common.business.interfaces.dto.enuns;

import com.yue.chip.common.business.definition.enums.EnumUtilDefinition;
//import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/7/6 下午1:40
 */
@Data
//@Schema(description = "枚举")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class EnumUtilDto extends EnumUtilDefinition {

    @Override
    @NotBlank(message = "编码不能为空")
    public String getCode() {
        return super.getCode();
    }

    @Override
    @NotBlank(message = "版本号不能为空")
    public String getVersion() {
        return super.getVersion();
    }

    @Override
    @NotBlank(message = "枚举值不能为空")
    public String getValue() {
        return super.getValue();
    }
}
