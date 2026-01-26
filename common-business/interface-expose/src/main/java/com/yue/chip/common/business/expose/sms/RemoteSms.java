package com.yue.chip.common.business.expose.sms;

import com.yue.chip.annotation.AuthorizationIgnore;
import com.yue.chip.core.ResultData;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

/**
 * @author Mr.Liu
 * @description: TODO0
 * @date 2023/11/1 下午2:33
 */
@HttpExchange("${http.exchange.host}")
public interface RemoteSms extends RemoteSmsDefinition {

    @Override
    @GetExchange(PREFIX+SEND_SINGLE)
    @AuthorizationIgnore
    ResultData sendSms(@RequestParam(value = "appId") String appId,
                       @RequestParam(value = "signName") String signName,
                       @RequestParam(value = "templateCode") String templateCode,
                       @RequestParam(value = "message") Object message,
                       @RequestParam(value = "phoneNumber") String phoneNumber);

    @Override
    @GetExchange(PREFIX+SEND)
    @AuthorizationIgnore
    ResultData sendSms(@RequestParam(value = "appId") String appId,
                 @RequestParam(value = "signName") String signName,
                 @RequestParam(value = "templateCode") String templateCode,
                 @RequestParam(value = "message") Object message,
                 @RequestParam(value = "phoneNumber") List<String> phoneNumbers);
}
