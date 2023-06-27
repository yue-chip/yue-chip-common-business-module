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
import org.hibernate.annotations.Comment;
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
@Table(name = "t_resources", indexes = {@Index(columnList = "parent_id"),@Index(columnList = "name")})
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@NoArgsConstructor
@Data
@Comment("菜单资源")
public class ResourcesPo extends ResourcesDefinition {

    @Override
    @Column(name = "parent_id", columnDefinition = "bigint NULL DEFAULT -9223372036854775808 COMMENT '父节点id'")
    public Long getParentId() {
        return super.getParentId();
    }

    @Override
    @Column(unique = true, columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '编码'")
    public String getCode() {
        return super.getCode();
    }

    @Override
    @Column(columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '名称'")
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
    @Column(columnDefinition = "bit(1) NULL DEFAULT 0 COMMENT '名称'")
    public Boolean getIsDefault() {
        return super.getIsDefault();
    }

    @Override
    @Column(columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT 'url'")
    public String getUrl() {
        return super.getUrl();
    }

    @Override
    @Column(columnDefinition = "int NULL DEFAULT 0 COMMENT '排序'")
    public Integer getSort() {
        return super.getSort();
    }

    @Override
    @Column(columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '备注'")
    public String getRemark() {
        return super.getRemark();
    }
}
