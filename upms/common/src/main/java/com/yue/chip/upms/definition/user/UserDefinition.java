package com.yue.chip.upms.definition.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.core.BaseDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@Schema(description = "用户")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true,value = {"password","accountNonExpired","accountNonLocked","credentialsNonExpired","enabled","createUserId","updateUserId","isDelete"})
public class UserDefinition extends BaseDefinition {

    public static final String CACHE_KEY = "user-";

    @Schema(description = "密码")
    private String password;

    @Schema(description = "用户登陆账号")
    private  String username;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "头像")
    private Long profilePhoto;

    @Schema(description = "出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private  boolean accountNonExpired;

    private  boolean accountNonLocked;

    private  boolean credentialsNonExpired;

    private  boolean enabled;

}
