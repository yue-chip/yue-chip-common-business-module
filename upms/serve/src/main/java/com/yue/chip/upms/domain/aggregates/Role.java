package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.upms.definition.aggregates.RoleARVODefinition;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.utils.SpringContextUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:13
 * @description 角色值对象/聚合根（角色-资源，角色就是聚合根）
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Role extends RoleARVODefinition {

    private  static volatile UpmsRepository upmsRepository;

    public Boolean checkNameIsExist() {
        Optional<Role> optional = upmsRepository.findRoleByName(getName());
        if (optional.isPresent()) {
            if (Objects.nonNull(getId()) && optional.get().getId().equals(getId())){
                return false;
            }
            return true;
        }
        return false;
    }

    private UpmsRepository getRepository() {
        if (Objects.isNull(upmsRepository)) {
            synchronized (this) {
                if (Objects.isNull(upmsRepository)) {
                    upmsRepository = (UpmsRepository) SpringContextUtil.getBean(UpmsRepository.class);
                }
            }
        }
        return upmsRepository;
    }
}
