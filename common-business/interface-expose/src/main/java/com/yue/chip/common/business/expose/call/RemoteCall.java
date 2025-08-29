package com.yue.chip.common.business.expose.call;

import com.yue.chip.common.business.expose.call.vo.QueryCallDetailByCallIdResponseBodyExposeVo;
import com.yue.chip.common.business.expose.call.vo.SingleCallByTtsResponseBodyExposeVo;
import com.yue.chip.core.ResultData;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/17 上午11:44
 */
@HttpExchange("${http.exchange.host}")
public interface RemoteCall extends RemoteCallDefinition{

    @Override
    @GetExchange(PREFIX+CALL)
    public ResultData<SingleCallByTtsResponseBodyExposeVo> call(String calledNumber, Object ttsParam, String ttsCode, Integer playTimes, Integer volume, String outId);

    @Override
    @GetExchange(PREFIX+CALL_RESULT)
    public ResultData<QueryCallDetailByCallIdResponseBodyExposeVo> callResult(String callId, Long prodId, Long queryDate );
}
