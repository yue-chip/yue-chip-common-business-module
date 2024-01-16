package com.yue.chip.upms.infrastructure.po.organizational;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.organizational.OrganizationalGroupDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author Mr.Liu
 * @description: 组织机构分组
 * @date 2023/10/7 下午2:24
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "T_ORGANIZATIONAL_GROUP",indexes = {@Index(columnList = "NAME")})
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@NoArgsConstructor
@Data
//@Comment("组织机构分组")
public class OrganizationalGroupPo extends OrganizationalGroupDefinition {

    @Override
    @Column(unique = true,name = "NAME")
    public String getName() {
        return super.getName();
    }
}
