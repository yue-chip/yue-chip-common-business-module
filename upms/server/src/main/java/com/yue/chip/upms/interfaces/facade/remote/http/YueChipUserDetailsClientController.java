package com.yue.chip.upms.interfaces.facade.remote.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yue.chip.core.ResultData;
import com.yue.chip.core.remote.http.YueChipUserDetailsClientDefinition;
import com.yue.chip.security.YueChipUserDetails;
import com.yue.chip.upms.domain.service.upms.UpmsDomainService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("")
@Validated
@Tag(name = "远程用户登陆")
@Log
public class YueChipUserDetailsClientController implements YueChipUserDetailsClientDefinition {

    @Resource
    private UpmsDomainService upmsDomainService;

    @Resource
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    @GetMapping(LOGIN_BY_USERNAME)
    public ResultData<YueChipUserDetails> loadUserByUsernameEx(String username) {
        ResultData.ResultDataBuilder<YueChipUserDetails> builder = ResultData.builder();
//        YueChipUserDetails userDetails = upmsDomainService.loadUserByUsername(username);
//        return objectMapper.writeValueAsString(userDetails);
        return builder.data(upmsDomainService.loadUserByUsername(username)).build();
    }

    @SneakyThrows
    @Override
    @GetMapping(LOGIN_BY_PHONE_NUMBER)
    public ResultData<YueChipUserDetails> loadUserByPhoneNumber(String phoneNumber) {
        ResultData.ResultDataBuilder<YueChipUserDetails> builder = ResultData.builder();
//        YueChipUserDetails userDetails = upmsDomainService.loadUserByPhoneNumber(phoneNumber);
//        return objectMapper.writeValueAsString(userDetails);
        return builder.data(upmsDomainService.loadUserByPhoneNumber(phoneNumber)).build();
    }

    @Override
    public ResultData<YueChipUserDetails> loadUserByEmail(@NotBlank(message = "邮箱不能为空") String s) {
        return null;
    }

    @Override
    public ResultData<YueChipUserDetails> loadUserBySocialTypeAndSocialUid(@NotBlank(message = "socialType不能为空") String s, @NotBlank(message = "socialUid不能为空") String s1) {
        return null;
    }

    @Override
    public ResultData<YueChipUserDetails> saveUserSocial(@NotBlank(message = "socialType不能为空") String s, @NotBlank(message = "socialUid不能为空") String s1, @NotBlank(message = "socialAcc不能为空") String s2, @NotBlank(message = "socialNickname不能为空") String s3) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
