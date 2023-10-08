package com.yue.chip.upms.infrastructure.po.organizational;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.organizational.OrganizationalUserDefinition;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
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
@Data
@Comment("组织机构与用户关联关系")
public class OrganizationalUserPo extends OrganizationalUserDefinition {

    @Override
    @Column(name = "user_id", columnDefinition = "bigint NULL DEFAULT 0 COMMENT '用户id'")
    public Long getUserId() {
        return super.getUserId();
    }

    @Override
    @Column(name = "organizational_id", columnDefinition = "bigint NULL DEFAULT 0 COMMENT '组织机构id'")
    public Long getOrganizationalId() {
        return super.getOrganizationalId();
    }

    @Override
    @Column(columnDefinition = "bit(1) NULL COMMENT '是否负责人'")
    public Boolean getIsManager() {
        return super.getIsManager();
    }
}
