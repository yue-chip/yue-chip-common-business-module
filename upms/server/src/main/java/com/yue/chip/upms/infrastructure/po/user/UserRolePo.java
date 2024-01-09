package com.yue.chip.upms.infrastructure.po.user;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.core.persistence.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
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
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@Comment("用户-角色关联表")
public class UserRolePo extends BaseEntity {

    @Setter
    private Long userId;

    @Setter
    private Long roleId;


    @Comment("用户id-不能为空")
    @ColumnDefault("-9223372036854775808")
    public Long getUserId() {
        return this.userId;
    }

    @Comment("角色id-不能为空")
    @ColumnDefault("-9223372036854775808")
    public Long getRoleId() {
        return this.roleId;
    }
}
