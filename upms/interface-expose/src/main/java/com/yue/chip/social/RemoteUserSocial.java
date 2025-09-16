package com.yue.chip.social;

import com.yue.chip.core.ResultData;
import com.yue.chip.social.vo.UserSocialExposeVo;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("${http.exchange.host}")
public interface RemoteUserSocial extends RemoteUserSocialDefinition{

    @Override
    @GetExchange(PREFIX+FIND)
    ResultData<UserSocialExposeVo> find(@RequestParam(value = "userId",required = false) @Parameter(description = "用户id") Long userId,
                                        @RequestParam(value = "type",required = false) @Parameter(description = "type") String type);
}
