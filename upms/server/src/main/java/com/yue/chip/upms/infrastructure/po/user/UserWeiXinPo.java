package com.yue.chip.upms.infrastructure.po.user;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.user.UserWeiXinDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * @author Mr.Liu
 * @description: 微信用户
 * @date 2023/10/7 下午5:33
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "T_USER_WEIXIN")
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@NoArgsConstructor
@Data
//@Comment("微信用户")
public class UserWeiXinPo extends UserWeiXinDefinition {

    @Override
    @Column(unique = true,name = "OPEN_ID")
    public String getOpenId() {
        return super.getOpenId();
    }

    @Override
    @Column(unique = true,name = "PHONE_NUMBER")
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    @Column(name = "TENANT_NUMBER")
    public Long getTenantNumber() {
        return super.getTenantNumber();
    }
}
