package com.yue.chip.common.business.expose.call;

import com.yue.chip.common.business.expose.call.vo.QueryCallDetailByCallIdResponseBodyExposeVo;
import com.yue.chip.common.business.expose.call.vo.SingleCallByTtsResponseBodyExposeVo;
import com.yue.chip.core.ResultData;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/17 上午11:44
 */
@HttpExchange("${http.exchange.host}")
public interface RemoteCall extends RemoteCallDefinition{

    @GetExchange(PREFIX+CALL)
    public ResultData<SingleCallByTtsResponseBodyExposeVo> call(@RequestParam(value = "calledNumber",required = false) String calledNumber,
                                                                @RequestParam(value = "ttsParam",required = false) Object ttsParam,
                                                                @RequestParam(value = "ttsCode",required = false) String ttsCode,
                                                                @RequestParam(value = "playTimes",required = false) Integer playTimes,
                                                                @RequestParam(value = "volume",required = false) Integer volume,
                                                                @RequestParam(value = "outId",required = false) String outId);

    @GetExchange(PREFIX+CALL_RESULT)
    public ResultData<QueryCallDetailByCallIdResponseBodyExposeVo> callResult(@RequestParam(value = "callId",required = false) String callId,
                                                                              @RequestParam(value = "prodId",required = false)  Long prodId,
                                                                              @RequestParam(value = "queryDate",required = false) Long queryDate );
}
