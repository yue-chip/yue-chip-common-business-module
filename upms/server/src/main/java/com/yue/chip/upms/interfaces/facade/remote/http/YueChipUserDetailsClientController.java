package com.yue.chip.upms.interfaces.facade.remote.http;

import com.yue.chip.core.ResultData;
import com.yue.chip.core.YueChipUserDetailsClient;
import com.yue.chip.security.YueChipUserDetails;
import com.yue.chip.upms.domain.service.upms.UpmsDomainService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/remote")
@Validated
@Tag(name = "远程用户登陆")
@Log
public class YueChipUserDetailsClientController implements YueChipUserDetailsClient {

    @Resource
    private UpmsDomainService upmsDomainService;

    @Override
    @GetMapping("/user/login/username")
    public ResultData<YueChipUserDetails> loadUserByUsernameEx(String username) throws UsernameNotFoundException {
        ResultData.ResultDataBuilder<YueChipUserDetails> builder = ResultData.builder();
        return builder.data(upmsDomainService.loadUserByUsername(username)).build();
    }

    @Override
    @GetMapping("/user/login/phone")
    public ResultData<YueChipUserDetails> loadUserByPhoneNumber(String phoneNumber) {
        ResultData.ResultDataBuilder<YueChipUserDetails> builder = ResultData.builder();
        return builder.data(upmsDomainService.loadUserByPhoneNumber(phoneNumber)).build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
