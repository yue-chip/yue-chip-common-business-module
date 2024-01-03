package com.yue.chip.upms.infrastructure.po.organizational;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.organizational.OrganizationalUserWeixinDefinition;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Mr.Liu
 * @description: 组织机构与微信用户关联关系
 * @date 2023/10/7 下午5:31
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_organizational_user_weixin",indexes = {@Index(columnList = "user_weixin_id"),@Index(columnList = "organizational_id")})
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@NoArgsConstructor
@Data
//@Comment("组织机构与微信用户关联关系")
@Deprecated
public class OrganizationalUserWeixinPo extends OrganizationalUserWeixinDefinition {

    @Override
    @Column(name = "user_weixin_id", columnDefinition = "bigint NULL DEFAULT 0 COMMENT '微信  用户id'")
    public Long getUserWeixinId() {
        return super.getUserWeixinId();
    }

    @Override
    @Column(name = "organizational_id", columnDefinition = "bigint NULL DEFAULT 0 COMMENT '组织机构id'")
    public Long getOrganizationalId() {
        return super.getOrganizationalId();
    }
}
