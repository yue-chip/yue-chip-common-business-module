package com.yue.chip.upms.infrastructure.assembler.user;

import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import org.mapstruct.Mapper;

/**
 * @author Mr.Liu
 * @date 2023/2/16 下午2:51
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public User userPoToUser(UserPo userPo);
}
