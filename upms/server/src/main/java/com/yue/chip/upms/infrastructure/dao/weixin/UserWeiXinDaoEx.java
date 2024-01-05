package com.yue.chip.upms.infrastructure.dao.weixin;

import com.yue.chip.upms.infrastructure.po.user.UserWeiXinPo;
import javax.validation.constraints.NotNull;

import java.util.Optional;

public interface UserWeiXinDaoEx {

    /**
     * @param id
     * @param tenantNumber
     * @return
     */
    Optional<UserWeiXinPo> findById(@NotNull Long id, @NotNull Long tenantNumber);
}
