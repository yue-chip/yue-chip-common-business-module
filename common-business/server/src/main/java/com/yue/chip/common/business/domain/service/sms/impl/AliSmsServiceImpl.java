//package com.yue.chip.common.business.domain.service.sms.impl;
//
//import com.aliyun.dysmsapi20170525.Client;
//import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
//import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.yue.chip.common.business.domain.service.sms.SmsService;
//import com.yue.chip.exception.BusinessException;
//import javax.annotation.Resource;
//import javax.validation.constraints.NotBlank;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
///**
// * @author Mr.Liu
// * @description: TODO
// * @date 2023/11/1 下午2:37
// */
//@Service
//@ConditionalOnProperty(prefix = "sms",name = "provider",havingValue = "aliyun")
//@ConditionalOnClass({Client.class})
//@Slf4j
//public class AliSmsServiceImpl implements SmsService {
//
//    @Resource
//    private Client client;
//
//    @Override
//    public Boolean sendSms(@NotBlank String appId, @NotBlank String signName, @NotBlank String templateCode, String message, List<String> phoneNumbers) {
//        if (Objects.isNull(phoneNumbers) || phoneNumbers.size() == 0) {
//            return true;
//        }
//        String result = phoneNumbers.stream().map(String::valueOf).collect(Collectors.joining(","));
//        SendSmsRequest sendSmsRequest = new SendSmsRequest();
//        sendSmsRequest.setTemplateCode(templateCode);
//        sendSmsRequest.setSignName(signName);
//        sendSmsRequest.setTemplateParam(message);
//        sendSmsRequest.setPhoneNumbers(result);
//        try {
//            SendSmsResponse response = client.sendSms(sendSmsRequest);
//            log.info("发送结果：".concat(new ObjectMapper().writeValueAsString(response.getBody())));
//        } catch (Exception e) {
//            e.printStackTrace();
//            BusinessException.throwException("发送失败："+e.getMessage());
//        }
//        return true;
//    }
//
//}
