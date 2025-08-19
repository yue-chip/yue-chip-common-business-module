package com.yue.chip.upms.application.expose.impl.social;

import com.yue.chip.core.Optional;
import com.yue.chip.social.UserSocialExposeService;
import com.yue.chip.social.vo.UserSocialExposeVo;
import com.yue.chip.upms.assembler.social.UserSocialMapper;
import com.yue.chip.upms.domain.aggregates.UserSocial;
import com.yue.chip.upms.domain.repository.social.UserSocialRepository;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Objects;

@DubboService(interfaceClass = UserSocialExposeService.class)
public class UserSocialExposeServiceImpl implements UserSocialExposeService {

    @Resource
    private UserSocialRepository userSocialRepository;

    @Resource
    private UserSocialMapper userSocialMapper;

    @Override
    public Optional<UserSocialExposeVo> findByUserIdAndType(Long userId, Long tenantNumber, String type) {
        if (Objects.isNull(userId)) {
            return Optional.empty();
        }
        java.util.Optional<UserSocial> optional = userSocialRepository.findByUserIdAndTenantNumberAndType(userId, tenantNumber, type);
        if (optional.isPresent()) {
           return Optional.builder().build().ofNullable(userSocialMapper.toUserSocialExposeVo(optional.get()));
        }
        return Optional.empty();
    }
}
