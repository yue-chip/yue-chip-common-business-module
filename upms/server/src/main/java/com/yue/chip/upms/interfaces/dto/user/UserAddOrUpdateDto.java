package com.yue.chip.upms.interfaces.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.yue.chip.core.persistence.Validator;
import com.yue.chip.upms.definition.user.UserDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/4/20 下午3:19
 */
@Data
@SuperBuilder
//@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class UserAddOrUpdateDto extends UserDefinition {

//    @NotBlank(message = "组织机构ID不能为空",groups = {Validator.Insert.class,Validator.Update.class})
//    @Schema(description = "组织机构ID")
    private List<Long> organizationalId;

    @NotBlank(message = "密码不能为空",groups = {Validator.Insert.class})
    //@Schema(description = "密码")
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

    @Override
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getCreateDateTime() {
        return super.getCreateDateTime();
    }

    @Override
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getLastLoginTime() {
        return super.getLastLoginTime();
    }

}
