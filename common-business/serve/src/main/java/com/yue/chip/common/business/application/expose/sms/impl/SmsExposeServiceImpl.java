package com.yue.chip.common.business.application.expose.sms.impl;

import com.yue.chip.common.business.domain.service.sms.SmsService;
import com.yue.chip.common.business.expose.sms.SmsExposeService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/1 下午2:36
 */
@DubboService(interfaceClass = SmsExposeService.class)
public class SmsExposeServiceImpl implements SmsExposeService {
    @Resource
    private SmsService smsService;

    @Override
    public Boolean sendSms(@NotBlank String appId, @NotBlank String signName, @NotBlank String templateCode, @NotBlank String message, String phoneNumber) {
        List<String> phoneNumbers = Arrays.asList(phoneNumber);
        return sendSms(appId, signName, templateCode, message, phoneNumbers);
    }

    @Override
    public Boolean sendSms(@NotBlank String appId, @NotBlank String signName, @NotBlank String templateCode, @NotBlank String message, List<String> phoneNumbers) {
        return smsService.sendSms(appId, signName, templateCode, message, phoneNumbers);
    }
}
