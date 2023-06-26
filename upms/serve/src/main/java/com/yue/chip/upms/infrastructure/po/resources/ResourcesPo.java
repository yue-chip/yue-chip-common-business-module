package com.yue.chip.upms.infrastructure.po.resources;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.JpaInterceptor;
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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:51
 * @description ResourcesPo
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_resources", indexes = {@Index(columnList = "parentId"),@Index(columnList = "name")})
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@NoArgsConstructor
@Data
public class ResourcesPo extends ResourcesDefinition {


    @Override
    public Long getParentId() {
        return super.getParentId();
    }

    @Override
    @Column(unique = true)
    public String getCode() {
        return super.getCode();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    @Convert(converter = Scope.ScopeConverter.class)
    public Scope getScope() {
        return super.getScope();
    }

    @Override
    @Convert(converter = Type.TypeConverter.class)
    public Type getType() {
        return super.getType();
    }

    @Override
    @Convert(converter = State.StateConverter.class)
    public State getState() {
        return super.getState();
    }

    @Override
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
