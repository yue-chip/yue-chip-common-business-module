package com.yue.chip.upms.application.expose.impl.weixin;

import com.yue.chip.core.Optional;
import com.yue.chip.upms.assembler.weixin.UserWeiXinMapper;
import com.yue.chip.upms.domain.aggregates.UserWeixin;
import com.yue.chip.upms.domain.repository.weixin.UserWeiXinRepository;
import com.yue.chip.weixin.UserWeiXinExposeService;
import com.yue.chip.weixin.vo.UserWeiXinExposeVo;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Objects;

@DubboService(interfaceClass = UserWeiXinExposeService.class)
public class UserWeiXinExposeServiceImpl implements UserWeiXinExposeService {

    @Resource
    private UserWeiXinRepository userWeiXinRepository;

    @Resource
    private UserWeiXinMapper userWeiXinMapper;

    @Override
    public Optional<UserWeiXinExposeVo> findById(Long id,  Long tenantNumber) {
        if (Objects.isNull(id)) {
            return Optional.empty();
        }
        java.util.Optional<UserWeixin> optional = userWeiXinRepository.findById(id,tenantNumber);
        if (optional.isPresent()) {
           return Optional.ofNullable(userWeiXinMapper.toUserWeiXinExposeVo(optional.get()));
        }
        return Optional.empty();
    }
}
