package com.yue.chip.upms.infrastructure.assembler.user;

import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.upms.interfaces.vo.user.UserListVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/2/16 下午2:51
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public User toUser(UserPo userPo);

    public List<UserListVo> toUserListVo(List<UserPo> list);

    public List<User> toUserList(List<UserPo> list);

    public UserPo toUserPo(User user);
}
