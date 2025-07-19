package com.yue.chip.common.business.interfaces.facade.remote.sms;

import com.yue.chip.common.business.domain.service.sms.SmsService;
import com.yue.chip.common.business.expose.sms.RemoteSms;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController()
@RequestMapping("/sms")
@Validated
@Tag(name = "远程发送短信")
@Log
public class RemoteSmsController implements RemoteSms {
    @Resource
    @Lazy
    private SmsService smsService;

    @Override
    public void sendSms(String appId, String signName, String templateCode, Object message, String phoneNumber) {
        List<String> phoneNumbers = Arrays.asList(phoneNumber);
        sendSms(appId, signName, templateCode, message, phoneNumbers);
    }

    @Override
    public void sendSms(String appId, String signName, String templateCode, Object message, List<String> phoneNumbers) {
        smsService.sendSms(appId, signName, templateCode, message, phoneNumbers);
    }
}
