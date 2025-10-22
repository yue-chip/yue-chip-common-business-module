package com.yue.chip.upms.interfaces.facade.login.weixin;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
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
import org.springframework.web.client.RestTemplate;

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

    @Resource
    private RestTemplate restTemplate;

    @PostMapping("/login")
    @AuthorizationIgnore
    @Operation(summary = "登录", description = "登录")
    public IResultData<String> login(@NotBlank(message = "登录账号不能为空") @Parameter(description = "登录账号",name = "username",required = true)String username,
                                     @NotBlank(message = "密码不能为空") @Parameter(description = "密码(MD5编码)",name = "password",required = true)String password) {
        try {
            Optional<TenantNumber> optional = TenantNumberUtil.getTenantNumberForNotLogin();
            if (optional.isPresent()) {
                CurrentUserUtil.setCurrentTenantNumber(optional.get());
            }
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

    @PostMapping("/login/grid")
    @AuthorizationIgnore
    @Operation(summary = "登录", description = "登录")
    public IResultData<String> loginGrid(@NotBlank(message = "登录账号不能为空") @Parameter(description = "登录账号",name = "username",required = true)String username) {
        try {
            Optional<TenantNumber> optional = TenantNumberUtil.getTenantNumberForNotLogin();
            String token = loginService.loginGrid(username);
            Map<String,String> map = new HashMap<>();
            map.put("token",token);
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

    @PostMapping("/login/yst")
    @AuthorizationIgnore
    @Operation(summary = "粤商通登录", description = "粤商通登录")
    public IResultData<?> loginYst(@NotBlank(message = "手机号不能为空") @Parameter(description = "手机号",name = "phoneNumber",required = true) String phoneNumber) {
       try {
        String token = loginService.loginYst(phoneNumber);
        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        Optional<UserPo> firstByPhoneNumber = userDao.findFirstByPhoneNumber(phoneNumber);
        try {
            if (firstByPhoneNumber.isPresent()) {
                systemLogService.saveLog("登录账号", firstByPhoneNumber.get().getId(), "pc");
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
            Optional<TenantNumber> optional = TenantNumberUtil.getTenantNumberForNotLogin();
            if (optional.isPresent()) {
                CurrentUserUtil.setCurrentTenantNumber(optional.get());
            }
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

    @GetMapping("/openid")
    @AuthorizationIgnore
    @Operation(summary = "获取openid", description = "获取openid")
    public IResultData<String> openId(String code){
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx9d61f9a8ccffbe2e&secret=b8d172fabbf7e0e92ba5931fbe5233f6&js_code=" + code + "&grant_type=authorization_code";
        String response = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = JSON.parseObject(response);
        return ResultData.builder().data(jsonObject.getString("openid")).build();
    }


    @GetMapping("/login/out")
    @AuthorizationIgnore
    @Operation(summary = "退出登录", description = "退出登录")
    public IResultData<String> loginOut() {
        loginService.loginOut();
        return ResultData.builder().build();
    }

}


//iptables -t nat -D PREROUTING --dst 172.16.0.16 -p tcp --dport 8910 -j DNAT --to-destination 202.105.182.199:8910
//iptables -t nat -D POSTROUTING --dst 202.105.182.199 -p tcp --dport 8910 -j SNAT --to-source 172.16.0.16



//iptables -t nat -A PREROUTING --dst 172.16.0.16 -p tcp --dport 8910 -j DNAT --to-destination 120.238.165.61:5478
//iptables -t nat -A POSTROUTING --dst 120.238.165.61 -p tcp --dport 5478 -j SNAT --to-source 172.16.0.16