package com.yue.chip.common.business.domain.service.sms.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yue.chip.common.business.domain.service.sms.SmsService;
import com.yue.chip.exception.BusinessException;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/1 下午2:37
 */
@Service
@ConditionalOnProperty(prefix = "sms",name = "provider",havingValue = "aliyun")
@ConditionalOnClass({Client.class})
@Slf4j
public class AliSmsServiceImpl implements SmsService {

    @Resource
    private Client client;

    @Override
    public Boolean sendSms(@NotBlank String appId, @NotBlank String signName, @NotBlank String templateCode, String message, List<String> phoneNumbers) {
        if (Objects.isNull(phoneNumbers) || phoneNumbers.size() == 0) {
            return true;
        }
        String result = phoneNumbers.stream().map(String::valueOf).collect(Collectors.joining(","));
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setTemplateCode(templateCode);
        sendSmsRequest.setSignName(signName);
        sendSmsRequest.setTemplateParam(message);
        sendSmsRequest.setPhoneNumbers(result);
        try {
            SendSmsResponse response = client.sendSms(sendSmsRequest);
            log.info("发送结果：".concat(new ObjectMapper().writeValueAsString(response.getBody())));
        } catch (Exception e) {
            e.printStackTrace();
            BusinessException.throwException("发送失败："+e.getMessage());
        }
        return true;
    }

//    public static void main(String[] args_) throws Exception {
//        // 请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID 和 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
//        // 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例使用环境变量获取 AccessKey 的方式进行调用，仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
//        Config config = new Config()
//                .setAccessKeyId("LTAIUVQ7pr8cbw7V")
//                .setAccessKeySecret("VxFEWQfhgWK7itKs3UJoSBV4ekDgK7");
//        // 访问的域名
//        config.endpoint = "dysmsapi.aliyuncs.com";
//        com.aliyun.dysmsapi20170525.Client client = new Client(config);
//        com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
//                .setPhoneNumbers("18928025540,13632184543")
//                .setSignName("小未智能")
//                .setTemplateCode("SMS_216403610")
//                .setTemplateParam("{'code':'123456'}");
//        try {
//            // 复制代码运行请自行打印 API 的返回值
//            SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
//            System.out.println(sendSmsResponse.getBody());
//        } catch (TeaException error) {
//            error.printStackTrace();
//            // 如有需要，请打印 error
//            com.aliyun.teautil.Common.assertAsString(error.message);
//        } catch (Exception _error) {
//            _error.printStackTrace();
//            TeaException error = new TeaException(_error.getMessage(), _error);
//            // 如有需要，请打印 error
//            com.aliyun.teautil.Common.assertAsString(error.message);
//        }
//    }

}
