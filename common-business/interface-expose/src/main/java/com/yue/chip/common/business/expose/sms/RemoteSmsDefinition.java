package com.yue.chip.common.business.expose.sms;

import com.yue.chip.annotation.AuthorizationIgnore;
import com.yue.chip.core.IResultData;
import com.yue.chip.core.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Mr.Liu
 * @description: TODO0
 * @date 2023/11/1 下午2:33
 */
public interface RemoteSmsDefinition {
    static final String PREFIX = "/common";

    static final String SEND_SINGLE = "/sms/remote/send/single";
    static final String SEND = "/sms/remote/send";

    /**
     * 发送短信
     *
     * @param appId        腾讯云appId  啊里云短信无需填写
     * @param signName     签名
     * @param templateCode 短信模板编码
     * @param message      短信内容 啊里云短信：{"code":"123456"},腾讯短信-用^隔开：刘方^123456
     * @param phoneNumber  手机号码 腾讯短信：示例如：+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号，最多不要超过200个手机号

     */
    @Operation(description = "发送短信",summary = "发送短信")
    @AuthorizationIgnore
    public ResultData sendSms(@NotBlank(message = "appId不能为空") @RequestParam("appId") String appId,
                              @NotBlank(message = "signName不能为空") @RequestParam("signName") String signName,
                              @NotBlank(message = "模板编码不能为空") @RequestParam("templateCode") String templateCode,
                              @RequestParam("message") Object message,
                              @NotBlank(message = "电话号码不能为空") @RequestParam("phoneNumber") String phoneNumber);

    /**
     * 发送短信
     *
     * @param appId        腾讯云appId  啊里云短信无需填写
     * @param signName     签名
     * @param templateCode 短信模板编码
     * @param message      短信内容 啊里云短信：{"code":"123456"},腾讯短信-用^隔开：刘方^123456
     * @param phoneNumbers 手机号码 示例如：+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号，最多不要超过200个手机号
     */
    @Operation(description = "发送短信",summary = "发送短信")
    @AuthorizationIgnore
    public ResultData sendSms(@NotBlank(message = "appId不能为空") @RequestParam("appId")String appId,
                        @NotBlank(message = "signName不能为空") @RequestParam("signName")String signName,
                        @NotBlank(message = "模板编码不能为空") @RequestParam("templateCode")String templateCode,
                        @RequestParam("message")Object message,
                        @NotNull(message = "电话号码不能为空") @Size(min = 1,message = "电话号码不能为空") @RequestParam("phoneNumbers")List<String> phoneNumbers);
}
