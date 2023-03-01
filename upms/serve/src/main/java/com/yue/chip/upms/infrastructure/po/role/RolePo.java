package com.yue.chip.upms.infrastructure.po.role;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.upms.definition.role.RoleDefinition;
import com.yue.chip.upms.enums.Scope;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:50
 * @description RolePo
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_role",indexes = {@Index(columnList = "code"),@Index(columnList = "name") })
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@SuperBuilder
@NoArgsConstructor
@Data
public class RolePo extends RoleDefinition {

    @Override
    @Column(nullable = false)
    @Convert(converter = State.StateConverter.class)
    public State getState() {
        return super.getState();
    }

    @Override
    @Column(unique = true,nullable = false)
    public String getCode() {
        return super.getCode();
    }

    @Override
    @Column(nullable = false)
    public String getName() {
        return super.getName();
    }

    @Override
    @Column(nullable = false)
    @Convert(converter = Scope.ScopeConverter.class)
    public Scope getScope() {
        return super.getScope();
    }

    @Override
    @Column(nullable = false)
    public Boolean getIsDefault() {
        return super.getIsDefault();
    }

    @Override
    public String getRemark() {
        return super.getRemark();
    }

    @Override
    public void setCode(String code) {
        super.setCode(code);
    }
}
