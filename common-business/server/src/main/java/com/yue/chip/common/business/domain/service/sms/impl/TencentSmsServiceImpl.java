package com.yue.chip.common.business.domain.service.sms.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.yue.chip.common.business.domain.service.sms.SmsService;
import com.yue.chip.exception.BusinessException;
import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/1 下午2:39
 */
@Service
@Slf4j
public class TencentSmsServiceImpl implements SmsService {

    @Resource
    @Lazy
    private SmsClient client;

    @Override
    public Boolean sendSms(@NotBlank String appId, @NotBlank String signName, @NotBlank String templateCode, @NotBlank Object message, List<String> phoneNumbers) {
        /* 实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数
         * 您可以直接查询SDK源码确定接口有哪些属性可以设置
         * 属性可能是基本类型，也可能引用了另一个数据结构
         * 推荐使用IDE进行开发，可以方便的跳转查阅各个接口和数据结构的文档说明 */
        SendSmsRequest req = new SendSmsRequest();

        /* 填充请求参数,这里request对象的成员变量即对应接口的入参
         * 您可以通过官网接口文档或跳转到request对象的定义处查看请求参数的定义
         * 基本类型的设置:
         * 帮助链接：
         * 短信控制台: https://console.cloud.tencent.com/smsv2
         * 腾讯云短信小助手: https://cloud.tencent.com/document/product/382/3773#.E6.8A.80.E6.9C.AF.E4.BA.A4.E6.B5.81 */

        /* 短信应用ID: 短信SdkAppId在 [短信控制台] 添加应用后生成的实际SdkAppId，示例如1400006666 */
        // 应用 ID 可前往 [短信控制台](https://console.cloud.tencent.com/smsv2/app-manage) 查看
        req.setSmsSdkAppId(appId);

        /* 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名 */
        // 签名信息可前往 [国内短信](https://console.cloud.tencent.com/smsv2/csms-sign) 或 [国际/港澳台短信](https://console.cloud.tencent.com/smsv2/isms-sign) 的签名管理查看
        req.setSignName(signName);

        /* 模板 ID: 必须填写已审核通过的模板 ID */
        // 模板 ID 可前往 [国内短信](https://console.cloud.tencent.com/smsv2/csms-template) 或 [国际/港澳台短信](https://console.cloud.tencent.com/smsv2/isms-template) 的正文模板管理查看
        req.setTemplateId(templateCode);

        /* 模板参数: 模板参数的个数需要与 TemplateId 对应模板的变量个数保持一致，若无模板参数，则设置为空 */
        req.setTemplateParamSet(converterMessage(message));

        /* 下发手机号码，采用 E.164 标准，+[国家或地区码][手机号]
         * 示例如：+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号，最多不要超过200个手机号 */
        req.setPhoneNumberSet(phoneNumbers.toArray(new String[phoneNumbers.size()]));

        /* 用户的 session 内容（无需要可忽略）: 可以携带用户侧 ID 等上下文信息，server 会原样返回 */
        String sessionContext = "";
        req.setSessionContext(sessionContext);

        /* 短信码号扩展号（无需要可忽略）: 默认未开通，如需开通请联系 [腾讯云短信小助手] */
        String extendCode = "";
        req.setExtendCode(extendCode);

        /* 国内短信无需填写该项；国际/港澳台短信已申请独立 SenderId 需要填写该字段，默认使用公共 SenderId，无需填写该字段。注：月度使用量达到指定量级可申请独立 SenderId 使用，详情请联系 [腾讯云短信小助手](https://cloud.tencent.com/document/product/382/3773#.E6.8A.80.E6.9C.AF.E4.BA.A4.E6.B5.81)。*/
        String senderid = "";
        req.setSenderId(senderid);

        /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
         * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */
        try {
            SendSmsResponse res = client.SendSms(req);
            log.info("sms发送结果：".concat(new ObjectMapper().writeValueAsString(res.getSendStatusSet())));
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
            BusinessException.throwException("发送失败："+e.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private String[] converterMessage(Object message) {
        String[] messageArray = new String[]{};
        if (message instanceof ArrayList<?>) {
            messageArray = (String[]) ((ArrayList)message).toArray();
        }else if (message instanceof String[]) {
            messageArray = (String[]) message;
        }else if (message instanceof String) {
            messageArray = ((String) message).split("\\^");
        }
        return messageArray;
    }
}
