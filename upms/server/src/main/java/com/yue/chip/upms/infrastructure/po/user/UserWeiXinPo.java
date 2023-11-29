package com.yue.chip.upms.infrastructure.po.user;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.user.UserWeiXinDefinition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
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
@Comment("微信用户")
public class UserWeiXinPo extends UserWeiXinDefinition {

    @Override
    @Column(name = "password", columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '密码-不能为空'")
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    @Column(name = "user_name", columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '登录帐号-不能为空'")
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    @Column(name = "name", columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '姓名'")
    public String getName() {
        return super.getName();
    }

    @Override
    @Column(name = "phone_number", columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '联系电话'")
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    @Column(columnDefinition = "bigint DEFAULT 1 COMMENT '租户编码'")
    public Long getTenantNumber() {
        return super.getTenantNumber();
    }
}
