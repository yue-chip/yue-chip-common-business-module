package com.yue.chip.common.business.expose.sms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @GetExchange(PREFIX+SEND_SINGLE)
    void sendSms(@NotBlank(message = "appId不能为空") @RequestParam("appId") String appId,
                 @NotBlank(message = "signName不能为空") @RequestParam("signName") String signName,
                 @NotBlank(message = "模板编码不能为空") @RequestParam("templateCode") String templateCode,
                 @NotBlank(message = "短信内容不能为空") @RequestParam("message") Object message,
                 @NotBlank(message = "电话号码不能为空") @RequestParam("phoneNumber") String phoneNumber);

    @GetExchange(PREFIX+SEND)
    void sendSms(@NotBlank(message = "appId不能为空") @RequestParam("appId")String appId,
                 @NotBlank(message = "signName不能为空") @RequestParam("signName")String signName,
                 @NotBlank(message = "模板编码不能为空") @RequestParam("templateCode")String templateCode,
                 @NotBlank(message = "短信内容不能为空") @RequestParam("message")Object message,
                 @NotNull(message = "电话号码不能为空") @Size(min = 1,message = "电话号码不能为空") @RequestParam("phoneNumbers")List<String> phoneNumbers);
}
