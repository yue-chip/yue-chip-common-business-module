package com.yue.chip.common.business.expose.call.vo;

import com.aliyun.core.annotation.NameInMap;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/27 下午3:35
 */
@Builder
@Data
public class QueryCallDetailByCallIdResponseBodyExposeVo implements Serializable {
    @NameInMap("Code")
    private String code;

    @NameInMap("Data")
    private String data;

    @NameInMap("Message")
    private String message;

    @NameInMap("RequestId")
    private String requestId;
}
