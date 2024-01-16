package com.yue.chip.upms.infrastructure.po.organizational;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.organizational.OrganizationalUserWeixinDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author Mr.Liu
 * @description: 组织机构与微信用户关联关系
 * @date 2023/10/7 下午5:31
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "T_ORGANIZATIONAL_USER_WEIXIN",indexes = {@Index(columnList = "USER_WEIXIN_ID"),@Index(columnList = "ORGANIZATIONAL_ID")})
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@NoArgsConstructor
@Data
//@Comment("组织机构与微信用户关联关系")
@Deprecated
public class OrganizationalUserWeixinPo extends OrganizationalUserWeixinDefinition {

    @Override
    @Column(name = "USER_WEIXIN_ID")
    public Long getUserWeixinId() {
        return super.getUserWeixinId();
    }

    @Override
    @Column(name = "ORGANIZATIONAL_ID")
    public Long getOrganizationalId() {
        return super.getOrganizationalId();
    }
}
