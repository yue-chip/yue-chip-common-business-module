package com.yue.chip.upms.assembler.user;

import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.upms.interfaces.dto.user.UserAddOrUpdateDto;
import com.yue.chip.upms.interfaces.vo.user.UserSelectVo;
import com.yue.chip.upms.interfaces.vo.user.UserVo;
import com.yue.chip.upms.vo.UserExposeVo;
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

    public List<User> toUser(List<UserPo> userPos);

//    public List<UserVo> toUserListVo(List<UserPo> listGrid);

    @Mappings({@Mapping(target = "organizationalName",source = "organizational.name"),
            @Mapping(target = "organizationalId",source = "organizational.id")})
    public List<UserVo> toUserListVo(List<User> list);

    @Mappings({@Mapping(target = "label",source = "name"),
            @Mapping(target = "value",source = "id")})
    public List<UserSelectVo> toUserSelectVo(List<User> list);

    @Mappings({@Mapping(target = "label",source = "name"),
            @Mapping(target = "value",source = "id")})
    public UserSelectVo toUserSelectVo(User user);

    public List<User> toUserList(List<UserPo> list);

    public UserPo toUserPo(User user);

    @Mappings({@Mapping(target = "password",source = "passwordI")})
    public UserPo toUserPo(UserAddOrUpdateDto userAddOrUpdateDto);

    public User toUser(UserAddOrUpdateDto userAddOrUpdateDto);

    public UserVo toUserVo(UserPo userPo);

    @Mappings({@Mapping(target = "organizationalName",source = "organizational.name"),
            @Mapping(target = "organizationalId",source = "organizational.id")})
    public UserVo toUserVo(User user);

    UserExposeVo toUserExposeVo(User userPo);

    List<UserExposeVo> toUserExposeVo(List<User> userList);
}
