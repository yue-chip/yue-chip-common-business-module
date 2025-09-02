package com.yue.chip.common.business.expose.call.vo;

import com.aliyun.core.annotation.NameInMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/27 下午3:34
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleCallByTtsResponseBodyExposeVo implements Serializable {
    @NameInMap("CallId")
    private String callId;

    @NameInMap("Code")
    private String code;

    @NameInMap("Message")
    private String message;

    @NameInMap("RequestId")
    private String requestId;
}
