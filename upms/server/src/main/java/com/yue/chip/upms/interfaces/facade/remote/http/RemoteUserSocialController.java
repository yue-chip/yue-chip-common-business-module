package com.yue.chip.upms.interfaces.facade.remote.http;

import com.yue.chip.core.ResultData;
import com.yue.chip.social.RemoteUserSocialDefinition;
import com.yue.chip.social.vo.UserSocialExposeVo;
import com.yue.chip.upms.assembler.social.UserSocialMapper;
import com.yue.chip.upms.domain.aggregates.UserSocial;
import com.yue.chip.upms.domain.repository.social.UserSocialRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController()
@RequestMapping("")
@Validated
@Tag(name = "")
@Log
public class RemoteUserSocialController implements RemoteUserSocialDefinition {

    @Resource
    private UserSocialRepository userSocialRepository;

    @Resource
    private UserSocialMapper userSocialMapper;


    @Override
    public ResultData<UserSocialExposeVo> find(Long userId, String type) {
        ResultData.ResultDataBuilder<UserSocialExposeVo> builder = ResultData.builder();
        if (Objects.isNull(userId)) {
            return builder.build();
        }
        Optional<UserSocial> optional = userSocialRepository.findByUserIdAndTenantNumberAndType(userId, type);
        if (optional.isPresent()) {
            return builder.data(userSocialMapper.toUserSocialExposeVo(optional.get())).build();
        }
        return builder.build();
    }
}
