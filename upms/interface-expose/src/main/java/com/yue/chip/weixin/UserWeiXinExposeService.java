package com.yue.chip.weixin;

import com.yue.chip.core.Optional;
import com.yue.chip.weixin.vo.UserWeiXinExposeVo;
import jakarta.validation.constraints.NotNull;

public interface UserWeiXinExposeService {

    /**
     * 根据id查寻微信用户
     *
     * @param id
     * @param tenantNumber
     * @return
     */
    Optional<UserWeiXinExposeVo> findById(@NotNull Long id,@NotNull Long tenantNumber);
}
