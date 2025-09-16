package com.yue.chip.social;

import com.yue.chip.annotation.AuthorizationIgnore;
import com.yue.chip.core.ResultData;
import com.yue.chip.social.vo.UserSocialExposeVo;
import io.swagger.v3.oas.annotations.Operation;

public interface RemoteUserSocialDefinition {

    static final String PREFIX = "/upms";

    static final String FIND = "/remote/user/find";

    @Operation(description = "根据id查寻微信用户",summary = "根据id查寻微信用户")
    @AuthorizationIgnore
    public ResultData<UserSocialExposeVo> find( Long userId,   String type);
}
