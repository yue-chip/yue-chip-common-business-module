package com.yue.chip.upms.infrastructure.po.user;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.user.UserSocialDefinition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author zak
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_user_social")
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@NoArgsConstructor
@Comment("微信用户")
public class UserSocialPo extends UserSocialDefinition {

    @Override
    @Column(unique = true)
    @Comment("用户ID不能为空")
    @ColumnDefault("0")
    public Long getUserId() {
        return super.getUserId();
    }

    @Override
    @Column(unique = true)
    @Comment("租户编号不能为空")
    @ColumnDefault("0")
    public Long getTenantNumber() {
        return super.getTenantNumber();
    }

    @Override
    @Column(unique = true)
    @Comment("第三方用户类型不能为空")
    @ColumnDefault("''")
    public String getType() {
        return super.getType();
    }

    @Override
    @Column(unique = true)
    @Comment("第三方用户ID不能为空")
    @ColumnDefault("''")
    public String getUid() {
        return super.getUid();
    }

    @Override
    @Column(unique = true)
    @Comment("第三方用户账号不能为空")
    @ColumnDefault("''")
    public String getAcc() {
        return super.getAcc();
    }

}
