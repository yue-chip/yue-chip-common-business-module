package com.yue.chip.upms.interfaces.facade.login.weixin;

import com.yue.chip.annotation.AuthorizationIgnore;
import com.yue.chip.core.IResultData;
import com.yue.chip.core.ResultData;
import com.yue.chip.upms.domain.service.login.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.Liu
 * @date 2023/5/25 上午11:43
 */
@RestController("weixinLoginController")
@RequestMapping()
@Validated
@Tag(name = "微信登录")
@Log
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/weixin/login")
    @AuthorizationIgnore
    @Operation(summary = "登录", description = "登录")
    public IResultData<String> login(@NotBlank(message = "登录账号不能为空") @Parameter(description = "登录账号",name = "username",required = true)String username,
                                     @NotBlank(message = "密码不能为空") @Parameter(description = "密码(MD5编码)",name = "password",required = true)String password) {
        String token = loginService.login(username,password);
        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        return ResultData.builder().data(map).build();
    }

}
