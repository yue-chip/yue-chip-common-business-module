package com.yue.chip.upms.interfaces.facade.login.weixin;

import com.yue.chip.upms.domain.service.login.LoginService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mr.Liu
 * @date 2023/5/25 上午11:43
 */
@RestController("weixinLoginController")
@RequestMapping("/weixin")
@Validated
@Tag(name = "微信登录")
@Log
public class LoginController {

    @Resource
    private LoginService loginService;
//
//    @PostMapping("/login")
//    @AuthorizationIgnore
//    @Operation(summary = "登录", description = "登录")
//    public IResultData<String> login(@NotBlank(message = "登录账号不能为空") @Parameter(description = "登录账号",name = "username",required = true)String username,
//                                     @NotBlank(message = "密码不能为空") @Parameter(description = "密码(MD5编码)",name = "password",required = true)String password) {
//        String token = loginService.login(username,password);
//        Map<String,String> map = new HashMap<>();
//        map.put("token",token);
//        return ResultData.builder().data(map).build();
//    }
//
//    @PostMapping("/login1")
//    @AuthorizationIgnore
//    @Operation(summary = "登录1", description = "登录1")
//    public IResultData<String> login1(@Parameter(description = "手机号码",name = "phoneNumber")String phoneNumber,
//            @NotBlank(message = "openId不能为空") @Parameter(description = "openId",name = "openId",required = true)String openId) {
//        String token = loginService.login1(phoneNumber,openId);
//        Map<String,String> map = new HashMap<>();
//        map.put("token",token);
//        return ResultData.builder().data(map).build();
//    }
//
//    @GetMapping("/login/out")
//    @AuthorizationIgnore
//    @Operation(summary = "退出登录", description = "退出登录")
//    public IResultData<String> loginOut() {
//        loginService.loginOut();
//        return ResultData.builder().build();
//    }

}
