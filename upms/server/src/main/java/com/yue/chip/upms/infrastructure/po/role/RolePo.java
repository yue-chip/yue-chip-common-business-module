package com.yue.chip.upms.infrastructure.po.role;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.role.RoleDefinition;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:50
 * @description RolePo
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_role",indexes = {@Index(columnList = "name"),@Index(columnList = "create_date_time"), @Index(columnList = "update_date_time")})
@SuperBuilder
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@Comment("角色")
public class RolePo extends RoleDefinition {

    @Override
    @Convert(converter = State.StateConverter.class)
    @Comment("状态(0:禁用,1:正常)-不能为空")
    @ColumnDefault("1")
    public State getState() {
        return super.getState();
    }

    @Override
    @Column(unique = true)
    @Comment("编码-不能为空")
    @ColumnDefault("''")
    public String getCode() {
        return super.getCode();
    }

    @Override
    @Comment("名称-不能为空")
    @ColumnDefault("''")
    public String getName() {
        return super.getName();
    }

    @Override
    @Comment("是否默认角色（0：否，1：是）默认角色不能删除-不能为空")
    @ColumnDefault("0")
    public Boolean getIsDefault() {
        return super.getIsDefault();
    }

    @Override
    @Comment("备注")
    @ColumnDefault("''")
    public String getRemark() {
        return super.getRemark();
    }
}
