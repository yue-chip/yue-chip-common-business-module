package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.annotation.YueChipDDDEntity;
import com.yue.chip.upms.definition.organizational.OrganizationalDefinition;
import com.yue.chip.upms.domain.repository.organizational.OrganizationalRepository;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalPo;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.glassfish.jaxb.core.v2.TODO;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/8 下午2:19
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@YueChipDDDEntity
public class Organizational extends OrganizationalDefinition {

    @Resource
    private OrganizationalRepository organizationalRepository;

    private List<User> users;

    public Boolean checkNameIsExist() {
        Assert.hasText(getName(),"机构名称不能为空");
        Assert.notNull(getParentId(),"父节点id不能为空");
        Optional<Organizational> optional = organizationalRepository.findByParentIdAndName(getParentId(),getName());
        //修改时判断
        if (optional.isPresent() && Objects.nonNull(getId())) {
            if (Objects.equals(optional.get().getId(),getId())) {
                return false;
            }
        }
        return optional.isPresent();
    }

    public List<User> getUsers() {
        // TODO
        return users;
    }
}
