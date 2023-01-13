package com.yue.chip.upms.infrastructure.po.user;

import com.yue.chip.core.persistence.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:52
 * @description UserRolePo
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_user_role",indexes = {@Index(columnList = "userId"),@Index(columnList = "roleId")})
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@SuperBuilder
@NoArgsConstructor
@Data
public class UserRolePo extends BaseEntity {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;

    @Column(nullable = false)
    public Long getUserId() {
        return this.userId;
    }

    @Column(nullable = false)
    public Long getRoleId() {
        return this.roleId;
    }
}
