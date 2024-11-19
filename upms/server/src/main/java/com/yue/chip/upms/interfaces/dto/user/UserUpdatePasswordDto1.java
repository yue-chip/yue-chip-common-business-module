package com.yue.chip.upms.interfaces.dto.user;

//import io.swagger.v3.oas.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author Mr.Liu
 * @date 2023/4/20 下午3:19
 */
@Data
@Builder
//@Schema
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUpdatePasswordDto1 {

    //@Schema(description = "用户id,不传则修改当前登录用户的密码")
    @NotBlank(message = "登陆用户名不能为空")
    private String username;

    //@Schema(description = "密码(md5编码)")
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    private String newPassword;

}
