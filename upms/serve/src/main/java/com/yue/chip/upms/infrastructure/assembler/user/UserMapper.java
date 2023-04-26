package com.yue.chip.upms.infrastructure.assembler.user;

import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.upms.interfaces.dto.user.UserAddOrUpdateDto;
import com.yue.chip.upms.interfaces.vo.user.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
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

    public List<UserVo> toUserListVo(List<UserPo> list);

    public List<User> toUserList(List<UserPo> list);

    public UserPo toUserPo(User user);

    @Mappings({@Mapping(target = "password",source = "passwordI")})
    public UserPo toUserPo(UserAddOrUpdateDto userAddOrUpdateDto);

    public User toUser(UserAddOrUpdateDto userAddOrUpdateDto);

    public UserVo toUserVo(User user);
}
