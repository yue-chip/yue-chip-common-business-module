package com.yue.chip.common.business.expose.call;

import com.yue.chip.common.business.expose.call.vo.QueryCallDetailByCallIdResponseBodyExposeVo;
import com.yue.chip.common.business.expose.call.vo.SingleCallByTtsResponseBodyExposeVo;
import com.yue.chip.core.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/17 上午11:44
 */
public interface RemoteCallDefinition {

    static final String PREFIX = "/common";

    static final String CALL = "/call/remote";
    static final String CALL_RESULT = "/call/remote";

    /**
     * @param calledNumber 被呼叫号码
     * @param ttsParam     呼叫内容
     * @param ttsCode      呼叫模板id
     * @param playTimes    内容播放次数
     * @param volume       音量
     * @param outId        标识一通呼叫
     * @return SingleCallByTtsResponseBody
     */
    @Operation(description = "拨打电话",summary = "拨打电话")
    public ResultData<SingleCallByTtsResponseBodyExposeVo> call(@RequestParam(value = "calledNumber",required = false) String calledNumber,
                                                                @RequestParam(value = "ttsParam",required = false) Object ttsParam,
                                                                @RequestParam(value = "ttsCode",required = false) String ttsCode,
                                                                @RequestParam(value = "playTimes",required = false) Integer playTimes,
                                                                @RequestParam(value = "volume",required = false) Integer volume,
                                                                @RequestParam(value = "outId",required = false) String outId);

    /**
     * @param callId    呼叫时返回的CallId
     * @param prodId    语音通知的ProdId
     * @param queryDate 查询指定时间点对应的24小时的记录
     * @return QueryCallDetailByCallIdResponseBody
     */
    @Operation(description = "查询拨打电话结果",summary = "查询拨打电话结果")
    public ResultData<QueryCallDetailByCallIdResponseBodyExposeVo> callResult(@RequestParam(value = "callId",required = false) String callId,
                                                                              @RequestParam(value = "prodId",required = false)  Long prodId,
                                                                              @RequestParam(value = "queryDate",required = false) Long queryDate );
}
