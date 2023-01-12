package com.yue.chip.upms.definition.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.core.BaseDefinition;
import com.yue.chip.core.persistence.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@Schema(description = "用户")
@MappedSuperclass
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true,value = {"password","accountNonExpired","accountNonLocked","credentialsNonExpired","enabled","createDateTime","updateDateTime","createUserId","updateUserId","isDelete"})
public class UserDefinition extends BaseDefinition {

    private String password;

    @Schema(description = "用户登陆账号")
    private  String username;

    @Schema(description = "姓名")
    private  String name;

    private  boolean accountNonExpired;

    private  boolean accountNonLocked;

    private  boolean credentialsNonExpired;

    private  boolean enabled;

    public UserDefinition(){}
}
