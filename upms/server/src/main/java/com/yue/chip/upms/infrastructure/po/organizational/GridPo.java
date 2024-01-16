package com.yue.chip.upms.infrastructure.po.organizational;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.organizational.GridDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/21 下午2:00
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "T_GRID",indexes = {@Index(columnList = "ORGANIZATIONAL_ID"),@Index(columnList = "USER_ID"),@Index(columnList = "NAME")})
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@NoArgsConstructor
@Data
//@Comment("网格")
public class GridPo extends GridDefinition {
    @Override
    @Column(name = "ORGANIZATIONAL_ID")
    public Long getOrganizationalId() {
        return super.getOrganizationalId();
    }

    @Override
    @Column(name = "USER_ID")
    public Long getUserId() {
        return super.getUserId();
    }

    @Override
    @Column(unique = true, name = "NAME")
    public String getName() {
        return super.getName();
    }
}
