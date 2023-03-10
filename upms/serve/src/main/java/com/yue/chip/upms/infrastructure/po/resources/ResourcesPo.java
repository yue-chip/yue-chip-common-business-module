package com.yue.chip.upms.infrastructure.po.resources;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.upms.definition.resources.ResourcesDefinition;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.enums.Type;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@Table(name = "t_resources", indexes = {@Index(columnList = "parentId"),@Index(columnList = "code"),@Index(columnList = "name")})
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@SuperBuilder
@NoArgsConstructor
public class ResourcesPo extends ResourcesDefinition {

    @Override
    @Column(nullable = false)
    public Long getParentId() {
        return super.getParentId();
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
    @Column(name = "type",nullable = false)
    @Convert(converter = Type.TypeConverter.class)
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

    @Override
    public String getUrl() {
        return super.getUrl();
    }

    @Override
    public Integer getSort() {
        return super.getSort();
    }

    @Override
    public String getRemark() {
        return super.getRemark();
    }
}
