package com.yue.chip.upms.infrastructure.dao.weixin;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.po.user.UserWeiXinPo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public interface UserWeiXinDao extends BaseDao<UserWeiXinPo>, UserWeiXinDaoEx {

    /**
     * 根据openid和手机号码查寻
     * @param openId
     * @param phoneNumber
     * @return
     */
    public Optional<UserWeiXinPo> findFirstByOpenIdAndPhoneNumber(@NotBlank String openId,@NotBlank String phoneNumber);

    /**
     * 根据openid
     * @param openId
     * @return
     */
    public Optional<UserWeiXinPo> findFirstByOpenId(@NotBlank String openId);

    List<UserWeiXinPo> findAllByPhoneNumber(@NotBlank String phoneNumber);
}
