package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.upms.assembler.user.UserMapper;
import com.yue.chip.upms.definition.organizational.GridDefinition;
import com.yue.chip.upms.domain.repository.organizational.OrganizationalRepository;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/21 下午2:04
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=false, doNotUseGetters = true)
@NoArgsConstructor
@Component

public class Grid extends GridDefinition {

    private static OrganizationalRepository organizationalRepository;

    private static UpmsRepository upmsRepository;

    private static UserMapper userMapper;

    /**
     * 网格管理员
     */
    private User user;

    public User getUser() {
        if (Objects.nonNull(user)) {
            return this.user;
        }
        if (Objects.isNull(getId())) {
            return null;
        }
        Optional<User> optional = upmsRepository.findUserByGridId(getId());
        return optional.isPresent()?optional.get():User.builder().build();
    }

    @Resource
    public  void setOrganizationalRepository(OrganizationalRepository organizationalRepository) {
        Grid.organizationalRepository = organizationalRepository;
    }

    @Resource
    public  void setUpmsRepository(UpmsRepository upmsRepository) {
        Grid.upmsRepository = upmsRepository;
    }

    @Resource
    public  void setUserMapper(UserMapper userMapper) {
        Grid.userMapper = userMapper;
    }
}
