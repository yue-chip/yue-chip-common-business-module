package com.yue.chip.common.business.interfaces.facade.remote.call;

import com.aliyun.sdk.service.dyvmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dyvmsapi20170525.models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yue.chip.common.business.expose.call.RemoteCall;
import com.yue.chip.common.business.expose.call.vo.QueryCallDetailByCallIdResponseBodyExposeVo;
import com.yue.chip.common.business.expose.call.vo.SingleCallByTtsResponseBodyExposeVo;
import com.yue.chip.core.ResultData;
import com.yue.chip.exception.BusinessException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController()
@RequestMapping("/call")
@Validated
@Tag(name = "远程拨打电话")
@Log
public class RemoteCallController implements RemoteCall {

    @Resource
    @Lazy
    private AsyncClient client;

    @Override
    public ResultData<SingleCallByTtsResponseBodyExposeVo> call(String calledNumber, Object ttsParam, String ttsCode, Integer playTimes, Integer volume, String outId) {
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
            ResultData.ResultDataBuilder<SingleCallByTtsResponseBodyExposeVo> resultData = ResultData.builder();
            return resultData.data(detail).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResultData<QueryCallDetailByCallIdResponseBodyExposeVo> callResult(String callId, Long prodId, Long queryDate) {
        QueryCallDetailByCallIdRequest queryCallDetailByCallIdRequest = QueryCallDetailByCallIdRequest.builder()
                .callId(callId)
                .prodId(prodId)
                .queryDate(queryDate)
                .build();
        ResultData.ResultDataBuilder<QueryCallDetailByCallIdResponseBodyExposeVo> resultData = ResultData.builder();
        try {
            CompletableFuture<QueryCallDetailByCallIdResponse> queryCallDetailByCallIdResponse = client.queryCallDetailByCallId(queryCallDetailByCallIdRequest);
            QueryCallDetailByCallIdResponseBody queryCallDetailByCallIdResponseBody = queryCallDetailByCallIdResponse.get().getBody();
            log.info("call结果查寻：".concat(new ObjectMapper().writeValueAsString(queryCallDetailByCallIdResponseBody)));
            QueryCallDetailByCallIdResponseBodyExposeVo detail = QueryCallDetailByCallIdResponseBodyExposeVo.builder().build();
            BeanUtils.copyProperties(queryCallDetailByCallIdResponseBody,detail);
            return resultData.data(detail).build();
        } catch (Exception e) {
            log.info("call结果查寻失败");
            e.printStackTrace();
            BusinessException.throwException("call结果查寻失败："+e.getMessage());
        }
        return  resultData.build();
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
