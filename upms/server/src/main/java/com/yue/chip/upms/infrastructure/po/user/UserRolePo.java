package com.yue.chip.upms.infrastructure.po.user;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.core.persistence.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:52
 * @description UserRolePo
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "T_USER_ROLE",indexes = {@Index(columnList = "USER_ID"),@Index(columnList = "ROLE_ID")})
@SuperBuilder
@NoArgsConstructor
@Data
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
//@Comment("用户-角色关联表")
public class UserRolePo extends BaseEntity {

    private Long userId;

    private Long roleId;

    @Column(name = "USER_ID")
    public Long getUserId() {
        return this.userId;
    }

    @Column(name = "ROLE_ID")
    public Long getRoleId() {
        return this.roleId;
    }
}
