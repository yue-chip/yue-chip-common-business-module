package com.yue.chip.common.business.interfaces.facade.remote.http.sms;

import com.yue.chip.common.business.domain.service.sms.SmsService;
import com.yue.chip.common.business.expose.sms.RemoteSmsDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController()
@RequestMapping()
@Validated
@Tag(name = "远程发送短信")
@Log
public class RemoteSmsController implements RemoteSmsDefinition {
    @Resource
    @Lazy
    private SmsService smsService;

    @Override
    @GetMapping(SEND_SINGLE)
    public void sendSms(@NotBlank(message = "appId不能为空") @RequestParam("appId") String appId,
                        @NotBlank(message = "signName不能为空") @RequestParam("signName") String signName,
                        @NotBlank(message = "模板编码不能为空") @RequestParam("templateCode") String templateCode,
                        @NotBlank(message = "短信内容不能为空") @RequestParam("message") Object message,
                        @NotBlank(message = "电话号码不能为空") @RequestParam("phoneNumber") String phoneNumber) {
        List<String> phoneNumbers = Arrays.asList(phoneNumber);
        sendSms(appId, signName, templateCode, message, phoneNumbers);
    }

    @Override
    @GetMapping(SEND)
    public void sendSms(@NotBlank(message = "appId不能为空") @RequestParam("appId")String appId,
                        @NotBlank(message = "signName不能为空") @RequestParam("signName")String signName,
                        @NotBlank(message = "模板编码不能为空") @RequestParam("templateCode")String templateCode,
                        @NotBlank(message = "短信内容不能为空") @RequestParam("message")Object message,
                        @NotNull(message = "电话号码不能为空") @Size(min = 1,message = "电话号码不能为空") @RequestParam("phoneNumbers")List<String> phoneNumbers) {
        smsService.sendSms(appId, signName, templateCode, message, phoneNumbers);
    }
}
