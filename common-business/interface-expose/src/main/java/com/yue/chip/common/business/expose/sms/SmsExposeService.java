package com.yue.chip.common.business.expose.sms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/1 下午2:33
 */
public interface SmsExposeService {

    /**
     * 发送短信
     *
     * @param appId        腾讯云appId  啊里云短信无需填写
     * @param signName     签名
     * @param templateCode 短信模板编码
     * @param message      短信内容 啊里云短信：{"code":"123456"},腾讯短信-用^隔开：刘方^123456
     * @param phoneNumber  手机号码 腾讯短信：示例如：+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号，最多不要超过200个手机号
     * @return
     */
    public Boolean sendSms(@NotBlank String appId, @NotBlank String signName, @NotBlank String templateCode, @NotBlank String message, @NotBlank String phoneNumber);

    /**
     * 发送短信
     *
     * @param appId        腾讯云appId  啊里云短信无需填写
     * @param signName     签名
     * @param templateCode 短信模板编码
     * @param message      短信内容 啊里云短信：{"code":"123456"},腾讯短信-用^隔开：刘方^123456
     * @param phoneNumbers 手机号码 示例如：+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号，最多不要超过200个手机号
     * @return
     */
    public Boolean sendSms(@NotBlank String appId, @NotBlank String signName,@NotBlank String templateCode, @NotBlank String message,@NotNull @Size(min = 1) List<String> phoneNumbers);
}
