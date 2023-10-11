package com.yue.chip.upms.interfaces.dto.user;

import com.yue.chip.core.persistence.Validator;
import com.yue.chip.upms.definition.user.UserDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/4/20 下午3:19
 */
@Data
@SuperBuilder
@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class UserAddOrUpdateDto extends UserDefinition {

//    @NotBlank(message = "组织机构ID不能为空",groups = {Validator.Insert.class,Validator.Update.class})
    @Schema(description = "组织机构ID")
    private Long organizationalId;

    @NotBlank(message = "密码不能为空",groups = {Validator.Insert.class})
    @Schema(description = "密码")
    private String passwordI;

    @Override
    @NotNull(message = "id不能为空",groups = {Validator.Update.class})
    public Long getId() {
        return super.getId();
    }

    @Override
    @NotBlank(message = "账号不能为空",groups = {Validator.Insert.class})
    public String getUsername() {
        return super.getUsername();
    }

    @Override
//    @NotBlank(message = "姓名不能为空",groups = {Validator.Update.class,Validator.Insert.class})
    public String getName() {
        return super.getName();
    }

    @Override
//    @NotBlank(message = "电话号码不能为空",groups = {Validator.Update.class,Validator.Insert.class})
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    //    @Override
//    @NotNull(message = "version不能为空",groups = {Validator.Update.class})
//    public Long getVersion() {
//        return super.getVersion();
//    }
}
