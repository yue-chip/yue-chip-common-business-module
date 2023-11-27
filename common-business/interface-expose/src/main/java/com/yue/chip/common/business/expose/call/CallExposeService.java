package com.yue.chip.common.business.expose.call;

import com.yue.chip.common.business.expose.call.vo.QueryCallDetailByCallIdResponseBodyExposeVo;
import com.yue.chip.common.business.expose.call.vo.SingleCallByTtsResponseBodyExposeVo;
import com.yue.chip.core.Optional;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/17 上午11:44
 */
public interface CallExposeService {

    /**
     * @param calledNumber 被呼叫号码
     * @param ttsParam     呼叫内容
     * @param ttsCode      呼叫模板id
     * @param playTimes    内容播放次数
     * @param volume       音量
     * @param outId        标识一通呼叫
     * @return SingleCallByTtsResponseBody
     */
    public Optional<SingleCallByTtsResponseBodyExposeVo> call(String calledNumber, Object ttsParam, String ttsCode, Integer playTimes, Integer volume, String outId);

    /**
     * @param callId    呼叫时返回的CallId
     * @param prodId    语音通知的ProdId
     * @param queryDate 查询指定时间点对应的24小时的记录
     * @return QueryCallDetailByCallIdResponseBody
     */
    public Optional<QueryCallDetailByCallIdResponseBodyExposeVo> callResult(String callId, Long prodId, Long queryDate );
}
