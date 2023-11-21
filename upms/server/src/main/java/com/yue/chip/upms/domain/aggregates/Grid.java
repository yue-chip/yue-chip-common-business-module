package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.annotation.YueChipDDDEntity;
import com.yue.chip.upms.definition.organizational.GridDefinition;
import com.yue.chip.upms.domain.repository.organizational.OrganizationalRepository;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/21 下午2:04
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@YueChipDDDEntity
public class Grid extends GridDefinition {

    @Resource
    private static OrganizationalRepository organizationalRepository;

    @Resource
    private static UpmsRepository upmsRepository;

    /**
     * 网格管理员
     */
    private User user;

    public User getUser() {
        if (Objects.isNull(getId())) {
            return null;
        }
        Optional<User> optional = upmsRepository.findUserByGridId(getId());
        return optional.isPresent()?optional.get():null;
    }
}
