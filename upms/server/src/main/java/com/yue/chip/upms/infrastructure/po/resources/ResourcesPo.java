package com.yue.chip.upms.infrastructure.po.resources;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.resources.ResourcesDefinition;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.enums.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:51
 * @description ResourcesPo
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_resources", indexes = {@Index(columnList = "parent_id"),@Index(columnList = "name"),@Index(columnList = "create_date_time"), @Index(columnList = "update_date_time")})
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@NoArgsConstructor
@Data
//@Comment("菜单资源")
public class ResourcesPo extends ResourcesDefinition {

    @Override
    @Column(name = "parent_id")
    public Long getParentId() {
        return super.getParentId();
    }

    @Override
    @Column(unique = true)
    public String getCode() {
        return super.getCode();
    }

    @Override
    @Column(name = "name")
    public String getName() {
        return super.getName();
    }

    @Override
    @Convert(converter = Scope.ScopeConverter.class)
    @Column(name = "scope")
    public Scope getScope() {
        return super.getScope();
    }

    @Override
    @Convert(converter = Type.TypeConverter.class)
    @Column(name = "type")
    public Type getType() {
        return super.getType();
    }

    @Override
    @Convert(converter = State.StateConverter.class)
    @Column(name = "state")
    public State getState() {
        return super.getState();
    }

    @Override
    @Column(name = "is_default")
    public Boolean getIsDefault() {
        return super.getIsDefault();
    }

    @Override
    @Column(name = "url")
    public String getUrl() {
        return super.getUrl();
    }

    @Override
    @Column(name = "sort")
    public Integer getSort() {
        return super.getSort();
    }

    @Override
    @Column(name = "remark")
    public String getRemark() {
        return super.getRemark();
    }

}
