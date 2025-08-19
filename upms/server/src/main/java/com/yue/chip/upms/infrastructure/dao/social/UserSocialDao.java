package com.yue.chip.upms.infrastructure.dao.social;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.po.user.UserSocialPo;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public interface UserSocialDao extends BaseDao<UserSocialPo>, UserSocialDaoEx {

    /**
     * 根据第三方用户类型和用户ID查询用户
     * @param type  第三方用户类型
     * @param uid   第三方用户ID
     * @return
     */
    Optional<UserSocialPo> findFirstByTypeAndUid(@NotBlank String type, @NotBlank String uid);

    Optional<UserSocialPo> findFirstByUserIdAndTenantNumberAndType(Long userId, Long tenantNumber, String type);
}
