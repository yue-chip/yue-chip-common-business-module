package com.yue.chip.upms.interfaces.facade.login.weixin;

import com.yue.chip.annotation.AuthorizationIgnore;
import com.yue.chip.core.IResultData;
import com.yue.chip.core.ResultData;
import com.yue.chip.core.SystemLogService;
import com.yue.chip.core.TenantNumber;
import com.yue.chip.upms.domain.service.login.LoginService;
import com.yue.chip.upms.infrastructure.dao.user.UserDao;
import com.yue.chip.upms.infrastructure.dao.weixin.UserWeiXinDao;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.upms.infrastructure.po.user.UserWeiXinPo;
import com.yue.chip.utils.CurrentUserUtil;
import com.yue.chip.utils.TenantNumberUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    @Resource
    private SystemLogService systemLogService;

    @Resource
    private UserDao userDao;

    @Resource
    private UserWeiXinDao userWeiXinDao;

    @PostMapping("/login")
    @AuthorizationIgnore
    @Operation(summary = "登录", description = "登录")
    public IResultData<String> login(@NotBlank(message = "登录账号不能为空") @Parameter(description = "登录账号",name = "username",required = true)String username,
                                     @NotBlank(message = "密码不能为空") @Parameter(description = "密码(MD5编码)",name = "password",required = true)String password) {
        try {
            CurrentUserUtil.setCurrentTenantNumber(TenantNumber.builder().tenantNumber(TenantNumberUtil.getTenantNumber1()).build());
            String token = loginService.login(username, password);
            Map<String, String> map = new HashMap<>();
            map.put("token", token);
            Optional<UserPo> firstByUsername = userDao.findFirstByUsername(username);
            try {
                if (firstByUsername.isPresent()) {
                    systemLogService.saveLog("登录账号", firstByUsername.get().getId(), "pc");
                }
            } catch (Exception e) {
                System.out.println("---------------");
                System.out.println(e.getMessage());
                System.out.println("---------------");
            }
            return ResultData.builder().data(map).build();
        }finally {
            CurrentUserUtil.cleanCurrentTenantNumber();
        }
    }

    @PostMapping("/login1")
    @AuthorizationIgnore
    @Operation(summary = "登录1", description = "登录1")
    public IResultData<String> login1(@Parameter(description = "手机号码",name = "phoneNumber")String phoneNumber,
            @NotBlank(message = "openId不能为空") @Parameter(description = "openId",name = "openId",required = true)String openId) {
        try {
            CurrentUserUtil.setCurrentTenantNumber(TenantNumber.builder().tenantNumber(TenantNumberUtil.getTenantNumber1()).build());
            String token = loginService.login1(phoneNumber, openId);
            Map<String, String> map = new HashMap<>();
            map.put("token", token);
            Optional<UserWeiXinPo> firstByUsername = userWeiXinDao.findFirstByOpenIdAndPhoneNumber(openId, phoneNumber);
            try {
                if (firstByUsername.isPresent()) {
                    systemLogService.saveLog("登录账号", firstByUsername.get().getId(), "wx");
                }
            } catch (Exception e) {
                System.out.println("---------------");
                System.out.println(e.getMessage());
                System.out.println("---------------");
            }
            return ResultData.builder().data(map).build();
        }finally {
            CurrentUserUtil.cleanCurrentTenantNumber();
        }
    }

    @GetMapping("/login/out")
    @AuthorizationIgnore
    @Operation(summary = "退出登录", description = "退出登录")
    public IResultData<String> loginOut() {
        loginService.loginOut();
        return ResultData.builder().build();
    }

}
