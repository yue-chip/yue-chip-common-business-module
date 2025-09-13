package com.yue.chip.remote.http.upms;

import com.yue.chip.annotation.AuthorizationIgnore;
import com.yue.chip.core.ResultData;
import com.yue.chip.remote.http.upms.vo.UserExposeVo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public interface RemoteUpmsDefinition {

    static final String PREFIX = "/upms";

    static final String FIND_USERNAME = "/remote/user/find/username";
    static final String FIND_PHONE = "/remote/user/find/phone";
    static final String FIND_EMAIL = "/remote/user/find/email";
    static final String REGISTER = "/remote/user/register";
    static final String REGISTER_EMAIL = "/remote/user/register/email";
    static final String REVOKED = "/remote/user/revoked";
    static final String UPDATE = "/remote/user/update";


    ResultData<UserExposeVo> findByUsername(@NotBlank String username);

    ResultData<UserExposeVo> findByPhoneNumber(@NotBlank String phoneNumber);

    ResultData<UserExposeVo> findByEmail(@NotBlank String email);

    @Operation(description = "APP用户注册或修改密码",summary = "APP用户注册或修改密码")
    @AuthorizationIgnore
    ResultData register(@NotBlank String phoneNumber, @NotBlank String password, String name, Long id);

    @Operation(description = "APP用户注册或修改密码(根据Email)",summary = "APP用户注册或修改密码(根据Email)")
    @AuthorizationIgnore
    ResultData registerByEmail(@NotBlank String email, @NotBlank String password, String name, Long id);

    @Operation(description = "注销账号",summary = "注销账号")
    @AuthorizationIgnore
    ResultData revoked(@NotNull Long userId);

    @Operation(description = "修改密码",summary = "修改密码")
    ResultData updateUserPassword(@NotNull Long userId, @NotNull String password);
}
