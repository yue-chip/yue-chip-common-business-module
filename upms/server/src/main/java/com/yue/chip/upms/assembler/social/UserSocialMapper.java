package com.yue.chip.upms.assembler.social;

import com.yue.chip.remote.http.social.vo.UserSocialExposeVo;
import com.yue.chip.upms.domain.aggregates.UserSocial;
import com.yue.chip.upms.infrastructure.po.user.UserSocialPo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserSocialMapper {

    UserSocialMapper INSTANCE = Mappers.getMapper(UserSocialMapper.class);

    UserSocial toUserSocial(UserSocialPo userWeiXinPo);

    UserSocialPo toUserSocialPo(UserSocial UserSocial);

    UserSocialExposeVo toUserSocialExposeVo(UserSocial UserSocial);
}
