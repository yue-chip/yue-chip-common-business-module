package com.yue.chip.upms.interfaces.facade.login.pc;

import cn.hutool.core.codec.Base64;
import com.yue.chip.annotation.AuthorizationIgnore;
import com.yue.chip.core.IResultData;
import com.yue.chip.core.ResultData;
import com.yue.chip.upms.application.service.UpmsApplication;
import com.yue.chip.core.controller.BaseController;
import com.yue.chip.core.controller.impl.BaseControllerImpl;
import com.yue.chip.upms.domain.service.login.LoginService;
import com.yue.chip.upms.interfaces.dto.user.UserAddOrUpdateDto;
import com.yue.chip.utils.CurrentUserUtil;
import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.Liu
 * @date 2023/5/25 上午11:43
 */
@RestController()
@RequestMapping()
@Validated
@Tag(name = "登录")
@Log
public class LoginController{

    @Resource
    private LoginService loginService;

    @Resource
    private UpmsApplication upmsApplication;


    @PostMapping("/login1")
    @AuthorizationIgnore
    //@Operation(summary = "登录", description = "登录")
    public IResultData<String> login(@NotBlank(message = "登录账号不能为空") @Parameter(description = "登录账号",name = "username",required = true)String username,
                                     @NotBlank(message = "密码不能为空") @Parameter(description = "密码",name = "password",required = true)String password) {
        String token = loginService.login(username,password);
        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        return ResultData.builder().data(map).build();
    }

    @PostMapping("/login2")
    @AuthorizationIgnore
    @Operation(summary = "登录", description = "登录")
    public IResultData<String> login2(String data) {
        CurrentUserUtil.setCurrentTenantNumber("48");
        String str = Base64.decodeStr(data);
        String username = str.split("&")[1].split("=")[1];
        String name = str.split("&")[2].split("=")[1];
        String password = "xiaowei123456";
        upmsApplication.saveUser1( UserAddOrUpdateDto.builder().name(name).username(username).password(password).passwordI(password).build());
        String token = loginService.login(username,password);
        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        CurrentUserUtil.cleanCurrentTenantNumber();
        return ResultData.builder().data(map).build();
    }

    @GetMapping("/login/out")
    @AuthorizationIgnore
    //@Operation(summary = "退出登录", description = "退出登录")
    public IResultData<String> loginOut() {
        loginService.loginOut();
        return ResultData.builder().build();
    }

}
