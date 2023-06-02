package com.yue.chip.upms.interfaces.facade.login;

import com.yue.chip.core.IResultData;
import com.yue.chip.core.ResultData;
import com.yue.chip.core.controller.BaseController;
import com.yue.chip.core.controller.impl.BaseControllerImpl;
import com.yue.chip.upms.domain.service.login.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.Liu
 * @date 2023/5/25 上午11:43
 */
@RestController()
@RequestMapping("/upms")
@Validated
@Tag(name = "登录")
@Log
public class LoginController extends BaseControllerImpl implements BaseController {

    @Resource
    private LoginService loginService;

    @RequestMapping("/login")
    @Operation(summary = "登录", description = "登录")
    public IResultData<String> login(@NotBlank(message = "登录账号不能为空") @Parameter(description = "登录账号",name = "username",required = true)String username,
                                     @NotBlank(message = "密码不能为空") @Parameter(description = "密码",name = "password",required = true)String password) {
        String token = loginService.login(username,password);
        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        return ResultData.builder().data(map).build();
    }

}
