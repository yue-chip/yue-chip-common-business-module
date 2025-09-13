package com.yue.chip.remote.http.social;

import com.yue.chip.core.ResultData;
import com.yue.chip.remote.http.social.vo.UserSocialExposeVo;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("${http.exchange.host}")
public interface RemoteUserSocial extends RemoteUserSocialDefinition {


    @Override
    @GetExchange(PREFIX+FIND)
    ResultData<UserSocialExposeVo> findByUserIdAndType(@RequestParam("userId") @Parameter(description = "用户id") Long userId,
                                                       @RequestParam("tenantNumber") @Parameter(description = "租户") Long tenantNumber,
                                                       @RequestParam("type") @Parameter(description = "类型")String type);
}
