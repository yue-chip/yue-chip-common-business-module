package com.yue.chip.common.business.interfaces.facade.remote.http.sms;

import com.yue.chip.common.business.domain.service.sms.SmsService;
import com.yue.chip.common.business.expose.sms.RemoteSmsDefinition;
import com.yue.chip.core.ResultData;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController()
@RequestMapping()
@Validated
@Tag(name = "远程发送短信")
@Slf4j
public class RemoteSmsController implements RemoteSmsDefinition {
    @Resource
    @Lazy
    private SmsService smsService;

    @Override
    @GetMapping(SEND_SINGLE)
    public ResultData sendSms(String appId, String signName, String templateCode, Object message, String phoneNumber) {
        log.info("发送短信(单条), appId={}, singName={}, tempCode={}, phoneNumber={}, message={}", appId, signName, templateCode, phoneNumber, message);
        List<String> phoneNumbers = Arrays.asList(phoneNumber);
        sendSms(appId, signName, templateCode, message, phoneNumbers);
        return ResultData.builder().data(true).build();
    }

    @Override
    @GetMapping(SEND)
    public ResultData sendSms(String appId, String signName, String templateCode, Object message, List<String> phoneNumbers) {
        log.info("发送短信(批量), appId={}, singName={}, tempCode={}, phoneNumbers={}, message={}", appId, signName, templateCode, phoneNumbers, message);
        smsService.sendSms(appId, signName, templateCode, message, phoneNumbers);
        return ResultData.builder().data(true).build();
    }
}
