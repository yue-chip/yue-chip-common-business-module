package com.yue.chip.upms.infrastructure.repository.social.impl;

import com.yue.chip.upms.assembler.social.UserSocialMapper;
import com.yue.chip.upms.domain.aggregates.UserSocial;
import com.yue.chip.upms.domain.repository.social.UserSocialRepository;
import com.yue.chip.upms.infrastructure.dao.social.UserSocialDao;
import com.yue.chip.upms.infrastructure.po.user.UserSocialPo;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserSocialRepositoryImpl implements UserSocialRepository {

    @Resource
    private UserSocialDao userSocialDao;

    @Resource
    private UserSocialMapper userWeiXinMapper;

    @Override
    public Optional<UserSocial> findByUserIdAndTenantNumberAndType(Long userId, Long tenantNumber, String type) {
        Optional<UserSocialPo> optional = userSocialDao.findFirstByUserIdAndTenantNumberAndType(userId, tenantNumber, type);
        return optional.map(userSocialPo -> userWeiXinMapper.toUserSocial(userSocialPo));
    }

    @Override
    public Optional<UserSocial> findByTypeAndUid(String openId, String phoneNumber) {
        Optional<UserSocialPo> optional = userSocialDao.findFirstByTypeAndUid(openId,phoneNumber);
        return optional.map(userSocialPo -> userWeiXinMapper.toUserSocial(userSocialPo));
    }

    @Override
    public UserSocialPo saveUserSocial(UserSocialPo userWeiXinPo) {
        return userSocialDao.save(userWeiXinPo);
    }

    @Override
    public void updateUserSocial(UserSocialPo userWeiXinPo) {
        userSocialDao.update(userWeiXinPo);
    }

}
