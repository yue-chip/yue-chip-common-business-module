package com.yue.chip.upms.infrastructure.po.user;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.core.persistence.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:52
 * @description UserRolePo
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_user_role",indexes = {@Index(columnList = "userId"),@Index(columnList = "roleId")})
@SuperBuilder
@NoArgsConstructor
@Data
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
public class UserRolePo extends BaseEntity {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;

    public Long getUserId() {
        return this.userId;
    }

    public Long getRoleId() {
        return this.roleId;
    }
}
