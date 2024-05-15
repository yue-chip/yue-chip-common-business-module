package com.yue.chip.upms.infrastructure.po.organizational;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.organizational.OrganizationalUserDefinition;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/7 下午2:25
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_organizational_user",indexes = {@Index(columnList = "user_id"),@Index(columnList = "organizational_id")})
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@NoArgsConstructor
@Comment("组织机构与用户关联关系")
public class OrganizationalUserPo extends OrganizationalUserDefinition {

    @Override
    @Comment("用户id")
    @ColumnDefault("0")
    public Long getUserId() {
        return super.getUserId();
    }

    @Override
    @Comment("组织机构id")
    @ColumnDefault("0")
    public Long getOrganizationalId() {
        return super.getOrganizationalId();
    }

}
