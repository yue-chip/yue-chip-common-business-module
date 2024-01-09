package com.yue.chip.upms.infrastructure.po.role;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.role.RoleDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

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
@Data
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
//@Comment("角色")
public class RolePo extends RoleDefinition {

    @Override
    @Convert(converter = State.StateConverter.class)
    @Column(name = "state")
    public State getState() {
        return super.getState();
    }

    @Override
    @Column(unique = true,name = "code")
    public String getCode() {
        return super.getCode();
    }

    @Override
    @Column(name = "name")
    public String getName() {
        return super.getName();
    }

    @Override
    @Column(name = "is_default")
    public Boolean getIsDefault() {
        return super.getIsDefault();
    }

    @Override
    @Column(name = "remark")
    public String getRemark() {
        return super.getRemark();
    }
}
