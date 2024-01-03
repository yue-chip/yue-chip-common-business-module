package com.yue.chip.upms.infrastructure.po.user;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.user.UserWeiXinDefinition;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
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
@Data
//@Comment("微信用户")
public class UserWeiXinPo extends UserWeiXinDefinition {

    @Override
    @Column(unique = true, name = "open_id", columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT 'open_id-不能为空'")
    public String getOpenId() {
        return super.getOpenId();
    }

    @Override
    @Column(unique = true,name = "phone_number", columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '联系电话'")
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    @Column(columnDefinition = "bigint DEFAULT 1 COMMENT '租户编码'")
    public Long getTenantNumber() {
        return super.getTenantNumber();
    }
}
