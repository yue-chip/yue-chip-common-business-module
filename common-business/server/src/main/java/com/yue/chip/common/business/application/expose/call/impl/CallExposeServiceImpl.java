package com.yue.chip.common.business.application.expose.call.impl;

import cn.hutool.core.bean.BeanUtil;
import com.aliyun.dyvmsapi20170525.Client;
import com.aliyun.dyvmsapi20170525.models.*;
import com.aliyun.teautil.models.RuntimeOptions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yue.chip.common.business.expose.call.CallExposeService;
import com.yue.chip.common.business.expose.call.vo.QueryCallDetailByCallIdResponseBodyExposeVo;
import com.yue.chip.common.business.expose.call.vo.SingleCallByTtsResponseBodyExposeVo;
import com.yue.chip.core.Optional;
import com.yue.chip.exception.BusinessException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.Map;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/17 下午2:08
 */
@DubboService(interfaceClass = CallExposeService.class)
@ConditionalOnProperty(prefix = "call",name = "provider",havingValue = "aliyun")
@ConditionalOnClass( {Client.class} )
@Slf4j
public class CallExposeServiceImpl implements CallExposeService {

    @Resource
    private Client client;

    @Override
    public Optional<SingleCallByTtsResponseBodyExposeVo> call(String calledNumber, Object ttsParam, String ttsCode, Integer playTimes, Integer volume, String outId) {
        SingleCallByTtsRequest singleCallByTtsRequest = new SingleCallByTtsRequest()
                .setCalledNumber(calledNumber)
                .setTtsCode(ttsCode)
                .setTtsParam(converterTtsParam(ttsParam))
                .setPlayTimes(playTimes)
                .setVolume(volume)
                .setOutId(outId);
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            SingleCallByTtsResponse singleCallByTtsResponse = client.singleCallByTtsWithOptions(singleCallByTtsRequest, runtime);
            SingleCallByTtsResponseBody body = singleCallByTtsResponse.getBody();
            log.info("call结果：".concat(new ObjectMapper().writeValueAsString(body)));
            SingleCallByTtsResponseBodyExposeVo detail = new SingleCallByTtsResponseBodyExposeVo();
            BeanUtils.copyProperties(body,detail);
            return Optional.ofNullable(detail);
        } catch (Exception e) {
            log.info("call失败");
            e.printStackTrace();
            BusinessException.throwException("call失败："+e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<QueryCallDetailByCallIdResponseBodyExposeVo> callResult(String callId, Long prodId, Long queryDate) {
        QueryCallDetailByCallIdRequest queryCallDetailByCallIdRequest = new QueryCallDetailByCallIdRequest();
        queryCallDetailByCallIdRequest
//        产品ID。取值：
//        11000000300006：语音通知。
//        11010000138001：语音验证码。
//        11000000300005：语音IVR向指定号码发起交互式语音通话。
//        11000000300009：语音SIP。
//        11030000180001：智能外呼
//        https://help.aliyun.com/zh/vms/developer-reference/api-dyvmsapi-2017-05-25-querycalldetailbycallid
                .setCallId(callId)
                .setProdId(prodId)
                .setQueryDate(queryDate);
        try {
            QueryCallDetailByCallIdResponse queryCallDetailByCallIdResponse = client.queryCallDetailByCallIdWithOptions(queryCallDetailByCallIdRequest,new RuntimeOptions());
            QueryCallDetailByCallIdResponseBody queryCallDetailByCallIdResponseBody = queryCallDetailByCallIdResponse.getBody();
            log.info("call结果查寻：".concat(new ObjectMapper().writeValueAsString(queryCallDetailByCallIdResponseBody)));
            QueryCallDetailByCallIdResponseBodyExposeVo detail = new QueryCallDetailByCallIdResponseBodyExposeVo();
            BeanUtils.copyProperties(queryCallDetailByCallIdResponseBody,detail);
            return Optional.ofNullable(detail);
        } catch (Exception e) {
            log.info("call结果查寻失败");
            e.printStackTrace();
            BusinessException.throwException("call结果查寻失败："+e.getMessage());
        }
        return Optional.empty();
    }

    private String converterTtsParam(Object ttsParam) {
        String returnTtsParam = "";
        if (ttsParam instanceof Map) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                returnTtsParam = objectMapper.writeValueAsString(ttsParam);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return returnTtsParam;
    }
}
