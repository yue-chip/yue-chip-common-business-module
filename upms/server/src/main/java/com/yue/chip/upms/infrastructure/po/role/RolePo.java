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
@Table(name = "T_ROLE",indexes = {@Index(columnList = "NAME"),@Index(columnList = "CREATE_DATE_TIME"), @Index(columnList = "UPDATE_DATE_TIME")})
@SuperBuilder
@NoArgsConstructor
@Data
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
//@Comment("角色")
public class RolePo extends RoleDefinition {

    @Override
    @Convert(converter = State.StateConverter.class)
    @Column(name = "STATE")
    public State getState() {
        return super.getState();
    }

    @Override
    @Column(unique = true,name = "CODE")
    public String getCode() {
        return super.getCode();
    }

    @Override
    @Column(name = "NAME")
    public String getName() {
        return super.getName();
    }

    @Override
    @Column(name = "IS_DEFAULT")
    public Boolean getIsDefault() {
        return super.getIsDefault();
    }

    @Override
    @Column(name = "REMARK")
    public String getRemark() {
        return super.getRemark();
    }
}
