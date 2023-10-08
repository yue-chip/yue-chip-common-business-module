package com.yue.chip.upms.infrastructure.dao.user;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.po.user.UserWeiXinPo;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/7 下午6:19
 */
public interface UserWeiXinDao extends BaseDao<UserWeiXinPo> {

    /**
     * 根据登录帐号查询微信用户帐号
     * @param username
     * @return
     */
    public Optional<UserWeiXinPo> findFirstByUsername(@NotBlank String username);
}
