package com.yue.chip.common.business.expose.sms;

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
    void sendSms(@RequestParam("appId") String appId,
                 @RequestParam("signName") String signName,
                 @RequestParam("templateCode") String templateCode,
                 @RequestParam("message") Object message,
                 @RequestParam("phoneNumber") String phoneNumber);

    @Override
    @GetExchange(PREFIX+SEND)
    void sendSms(@RequestParam("appId") String appId,
                 @RequestParam("signName") String signName,
                 @RequestParam("templateCode") String templateCode,
                 @RequestParam("message") Object message,
                 @RequestParam("phoneNumbers") List<String> phoneNumbers);
}
