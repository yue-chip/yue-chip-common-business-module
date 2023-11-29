package com.yue.chip.upms.infrastructure.repository.weixin;

import com.yue.chip.upms.assembler.weixin.UserWeiXinMapper;
import com.yue.chip.upms.domain.aggregates.UserWeixin;
import com.yue.chip.upms.domain.repository.weixin.UserWeiXinRepository;
import com.yue.chip.upms.infrastructure.dao.weixin.UserWeiXinDao;
import com.yue.chip.upms.infrastructure.po.user.UserWeiXinPo;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserWeiXinRepositoryImpl implements UserWeiXinRepository {

    @Resource
    private UserWeiXinDao userWeiXinDao;

    @Resource
    private UserWeiXinMapper userWeiXinMapper;

    @Override
    public Optional<UserWeixin> findByOpenIdAndPhoneNumber(String openId, String phoneNumber) {
        Optional<UserWeiXinPo> optional = userWeiXinDao.findFirstByOpenIdAndPhoneNumber(openId,phoneNumber);
        if (optional.isPresent()) {
            return Optional.ofNullable(userWeiXinMapper.toUserWeiXin(optional.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserWeixin> findByOpenId(String openId) {
        Optional<UserWeiXinPo> optional = userWeiXinDao.findFirstByOpenId(openId);
        if (optional.isPresent()) {
            return Optional.ofNullable(userWeiXinMapper.toUserWeiXin(optional.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserWeixin> findById(@NotBlank Long id) {
        Optional<UserWeiXinPo> optional = userWeiXinDao.findById(id);
        if (optional.isPresent()) {
            Optional.ofNullable(userWeiXinMapper.toUserWeiXin(optional.get()));
        }
        return Optional.empty();
    }

    @Override
    public UserWeiXinPo saveUserWeiXin(UserWeiXinPo userWeiXinPo) {
        return userWeiXinDao.save(userWeiXinPo);
    }

    @Override
    public void updateUserWeiXin(UserWeiXinPo userWeiXinPo) {
        userWeiXinDao.update(userWeiXinPo);
    }
}
