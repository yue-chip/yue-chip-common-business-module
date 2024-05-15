package com.yue.chip.upms.assembler.weixin;

import com.yue.chip.upms.domain.aggregates.UserWeixin;
import com.yue.chip.upms.infrastructure.po.user.UserWeiXinPo;
import com.yue.chip.weixin.vo.UserWeiXinExposeVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserWeiXinMapper {

    UserWeiXinMapper INSTANCE = Mappers.getMapper(UserWeiXinMapper.class);

    public UserWeixin toUserWeiXin(UserWeiXinPo userWeiXinPo);

    public UserWeiXinPo toUserWeiXinPo(UserWeixin userWeixin);

    public UserWeiXinExposeVo toUserWeiXinExposeVo(UserWeixin userWeixin);
}
