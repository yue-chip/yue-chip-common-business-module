package com.yue.chip.upms.infrastructure.po.resources;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.upms.definition.resources.ResourcesDefinition;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.enums.Type;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:51
 * @description ResourcesPo
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_resources", indexes = {@Index(columnList = "parent_id"),@Index(columnList = "code"),@Index(columnList = "name")})
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Data
@SuperBuilder
public class ResourcesPo extends ResourcesDefinition {

    @Override
    @Column(name = "parent_id",nullable = false)
    public Long getParentId() {
        return super.getParentId();
    }

    @Override
    @Column(name = "code",unique = true,nullable = false)
    public String getCode() {
        return super.getCode();
    }

    @Override
    @Column(name = "name",nullable = false)
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
    @Column(name = "type",nullable = false)
    @Convert(converter = State.StateConverter.class)
    public Type getType() {
        return super.getType();
    }

    @Override
    @Column(name = "state",nullable = false)
    @Convert(converter = State.StateConverter.class)
    public State getState() {
        return super.getState();
    }

    @Override
    @Column(nullable = false)
    public Boolean getIsDefault() {
        return super.getIsDefault();
    }

    public ResourcesPo() {
        super();
    }
}
