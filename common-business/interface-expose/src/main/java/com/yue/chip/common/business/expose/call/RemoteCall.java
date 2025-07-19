package com.yue.chip.common.business.expose.call;

import com.yue.chip.common.business.expose.call.vo.QueryCallDetailByCallIdResponseBodyExposeVo;
import com.yue.chip.common.business.expose.call.vo.SingleCallByTtsResponseBodyExposeVo;
import com.yue.chip.core.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/17 上午11:44
 */
@HttpExchange("${http.exchange.host}")
public interface RemoteCall {

    /**
     * @param calledNumber 被呼叫号码
     * @param ttsParam     呼叫内容
     * @param ttsCode      呼叫模板id
     * @param playTimes    内容播放次数
     * @param volume       音量
     * @param outId        标识一通呼叫
     * @return SingleCallByTtsResponseBody
     */
    @GetExchange("/api/common/call/remote")
    @GetMapping("/remote")
    @Operation(description = "拨打电话",summary = "拨打电话")
    public ResultData<SingleCallByTtsResponseBodyExposeVo> call(String calledNumber, Object ttsParam, String ttsCode, Integer playTimes, Integer volume, String outId);

    /**
     * @param callId    呼叫时返回的CallId
     * @param prodId    语音通知的ProdId
     * @param queryDate 查询指定时间点对应的24小时的记录
     * @return QueryCallDetailByCallIdResponseBody
     */
    @GetExchange("/api/common/call/remote/result")
    @GetMapping("/remote/result")
    @Operation(description = "查询拨打电话结果",summary = "查询拨打电话结果")
    public ResultData<QueryCallDetailByCallIdResponseBodyExposeVo> callResult(String callId, Long prodId, Long queryDate );
}
