package com.yue.chip.common.business.expose.sms;

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
    void sendSms(String appId, String signName, String templateCode, Object message, String phoneNumber);

    @Override
    @GetExchange(PREFIX+SEND)
    void sendSms(String appId, String signName, String templateCode, Object message, List<String> phoneNumbers);
}
