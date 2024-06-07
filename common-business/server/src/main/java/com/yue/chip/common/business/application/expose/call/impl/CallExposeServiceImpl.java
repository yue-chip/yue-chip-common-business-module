package com.yue.chip.common.business.application.expose.call.impl;

import com.aliyun.sdk.service.dyvmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dyvmsapi20170525.models.*;
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
import org.springframework.context.annotation.Lazy;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/17 下午2:08
 */
@DubboService(interfaceClass = CallExposeService.class)
@Slf4j
public class CallExposeServiceImpl implements CallExposeService {

    @Resource
    @Lazy
    private AsyncClient client;

    @Override
    public Optional<SingleCallByTtsResponseBodyExposeVo> call(String calledNumber, Object ttsParam, String ttsCode, Integer playTimes, Integer volume, String outId) {
        SingleCallByTtsRequest request = SingleCallByTtsRequest.builder()
                .calledNumber(calledNumber)
                .ttsCode(ttsCode)
                .ttsParam(converterTtsParam(ttsParam))
                .playTimes(playTimes)
                .volume(volume)
                .outId(outId)
                .build();
                // Asynchronously get the return value of the API request
        CompletableFuture<SingleCallByTtsResponse> response = client.singleCallByTts(request);
        // Synchronously get the return value of the API request
        try {
            SingleCallByTtsResponse resp = response.get();
            SingleCallByTtsResponseBody body = resp.getBody();
            log.info("call结果：".concat(new ObjectMapper().writeValueAsString(body)));
            SingleCallByTtsResponseBodyExposeVo detail = SingleCallByTtsResponseBodyExposeVo.builder().build();
            BeanUtils.copyProperties(body,detail);
            return Optional.ofNullable(detail);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<QueryCallDetailByCallIdResponseBodyExposeVo> callResult(String callId, Long prodId, Long queryDate) {
        QueryCallDetailByCallIdRequest queryCallDetailByCallIdRequest = QueryCallDetailByCallIdRequest.builder()
                .callId(callId)
                .prodId(prodId)
                .queryDate(queryDate)
                .build();
        try {
            CompletableFuture<QueryCallDetailByCallIdResponse> queryCallDetailByCallIdResponse = client.queryCallDetailByCallId(queryCallDetailByCallIdRequest);
            QueryCallDetailByCallIdResponseBody queryCallDetailByCallIdResponseBody = queryCallDetailByCallIdResponse.get().getBody();
            log.info("call结果查寻：".concat(new ObjectMapper().writeValueAsString(queryCallDetailByCallIdResponseBody)));
            QueryCallDetailByCallIdResponseBodyExposeVo detail = QueryCallDetailByCallIdResponseBodyExposeVo.builder().build();
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
