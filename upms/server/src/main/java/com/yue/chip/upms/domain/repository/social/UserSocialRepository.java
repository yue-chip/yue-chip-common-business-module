package com.yue.chip.upms.domain.repository.social;

import com.yue.chip.upms.domain.aggregates.UserSocial;
import com.yue.chip.upms.infrastructure.po.user.UserSocialPo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface UserSocialRepository {

    /**
     * @param userId        用户ID
     * @param tenantNumber  租户编码
     * @param type          第三方用户类型
     * @return
     */
    Optional<UserSocial> findByUserIdAndTenantNumberAndType(Long userId, Long tenantNumber, @NotBlank String type);

    /**
     * @param type  第三方用户类型
     * @param uid   第三方用户UID
     * @return
     */
    Optional<UserSocial> findByTypeAndUid(@NotBlank String type, @NotBlank String uid);

    /**
     * 保存第三方用户
     * @param UserSocialPo
     * @return
     */
    UserSocialPo saveUserSocial(@NotNull UserSocialPo UserSocialPo);

    /**
     * 更新第三方用户
     * @param UserSocialPo
     * @return
     */
    void updateUserSocial(@NotNull UserSocialPo UserSocialPo);
}
