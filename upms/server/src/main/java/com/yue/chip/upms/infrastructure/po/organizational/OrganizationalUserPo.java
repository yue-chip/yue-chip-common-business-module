package com.yue.chip.upms.infrastructure.po.organizational;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.organizational.OrganizationalUserDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/7 下午2:25
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "T_ORGANIZATIONAL_USER",indexes = {@Index(columnList = "USER_ID"),@Index(columnList = "ORGANIZATIONAL_ID")})
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@NoArgsConstructor
@Data
//@Comment("组织机构与用户关联关系")
public class OrganizationalUserPo extends OrganizationalUserDefinition {

    @Override
    @Column(name = "USER_ID")
    public Long getUserId() {
        return super.getUserId();
    }

    @Override
    @Column(name = "ORGANIZATIONAL_ID")
    public Long getOrganizationalId() {
        return super.getOrganizationalId();
    }

}
