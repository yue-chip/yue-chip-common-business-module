package com.yue.chip.remote.http.social;

import com.yue.chip.core.ResultData;
import com.yue.chip.remote.http.social.vo.UserSocialExposeVo;
import io.swagger.v3.oas.annotations.Operation;

public interface RemoteUserSocialDefinition {

    static final String PREFIX = "/upms";

    static final String FIND = "/remote/user/social/find";

    @Operation(description = "根据id查寻微信用户",summary = "根据id查寻微信用户")
    ResultData<UserSocialExposeVo> findByUserIdAndType(Long userId, Long tenantNumber, String type);
}
