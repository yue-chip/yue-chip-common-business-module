package com.yue.chip.upms.assembler.user;

import com.yue.chip.upms.domain.aggregates.UserWeixin;
import com.yue.chip.upms.infrastructure.po.user.UserWeiXinPo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/7 下午6:30
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Deprecated
public interface UserWeixinMapper {

    UserWeixinMapper INSTANCE = Mappers.getMapper(UserWeixinMapper.class);

    public UserWeixin toUserWeixin(UserWeiXinPo userWeiXinPo);
}
