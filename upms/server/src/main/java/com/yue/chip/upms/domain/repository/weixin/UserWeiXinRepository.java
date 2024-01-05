package com.yue.chip.upms.domain.repository.weixin;

import com.yue.chip.upms.domain.aggregates.UserWeixin;
import com.yue.chip.upms.infrastructure.po.user.UserWeiXinPo;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.Optional;

public interface UserWeiXinRepository {

    /**
     * @param openId
     * @param phoneNumber
     * @return
     */
    public Optional<UserWeixin> findByOpenIdAndPhoneNumber(@NotBlank String openId, @NotBlank String phoneNumber);

    /**
     * @param openId
     * @return
     */
    public Optional<UserWeixin> findByOpenId(@NotBlank String openId);

    /**
     * @param id
     * @return
     */
    public Optional<UserWeixin> findById(@NotBlank Long id);

    /**
     * 保存微信用户
     * @param userWeiXinPo
     * @return
     */
    public UserWeiXinPo saveUserWeiXin(@NotNull UserWeiXinPo userWeiXinPo);

    /**
     * 更新微信用户
     * @param userWeiXinPo
     * @return
     */
    public void updateUserWeiXin(@NotNull UserWeiXinPo userWeiXinPo);

    /**
     * 根据id获取微信用户
     * @param id
     * @param tenantNumber
     * @return
     */
    Optional<UserWeixin> findById(@NotNull Long id, @NotNull Long tenantNumber);
}
