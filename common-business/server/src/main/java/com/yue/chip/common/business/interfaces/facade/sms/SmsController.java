package com.yue.chip.common.business.interfaces.facade.sms;

import com.yue.chip.annotation.AuthorizationIgnore;
import com.yue.chip.common.business.domain.service.sms.SmsService;
import com.yue.chip.core.IResultData;
import com.yue.chip.core.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/6 上午10:00
 */
@RestController()
@RequestMapping("/sms")
@Validated
//@Tag(name = "短信发送")
@Slf4j
public class SmsController {

    @Resource
    private SmsService smsService;

    @GetMapping("/send")
    //@Operation(description = "测试发送短信",summary = "测试发送短信")
    @AuthorizationIgnore
    public IResultData send(@NotBlank(message = "手机号码不能为空") String phoneNumber) {
        smsService.sendSms("1400813276","小未科技","1795028","测试设备^测试告警^2023-11-06 12:00:00", Arrays.asList("+86".concat(phoneNumber)));
        return ResultData.builder().build();
    }
}
