package com.yue.chip.upms.infrastructure.po.user;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.user.UserWeiXinDefinition;
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
 * @author Mr.Liu
 * @description: 微信用户
 * @date 2023/10/7 下午5:33
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_user_weixin")
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@NoArgsConstructor
@Comment("微信用户")
public class UserWeiXinPo extends UserWeiXinDefinition {

    @Override
    @Column(unique = true)
    @Comment("open_id-不能为空")
    @ColumnDefault("''")
    public String getOpenId() {
        return super.getOpenId();
    }

    @Override
    @Column(unique = true)
    @Comment("联系电话")
    @ColumnDefault("''")
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    @Comment("租户编码")
    public Long getTenantNumber() {
        return super.getTenantNumber();
    }
}
