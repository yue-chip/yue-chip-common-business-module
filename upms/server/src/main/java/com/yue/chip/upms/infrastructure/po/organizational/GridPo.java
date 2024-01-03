package com.yue.chip.upms.infrastructure.po.organizational;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.organizational.GridDefinition;
import jakarta.enterprise.inject.Default;
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
 * @date 2023/11/21 下午2:00
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_grid",indexes = {@Index(columnList = "organizational_id"),@Index(columnList = "user_id"),@Index(columnList = "name")})
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@NoArgsConstructor
@Data
//@Comment("网格")
public class GridPo extends GridDefinition {
    @Override
    @Column(name = "organizational_id", columnDefinition = "bigint NULL DEFAULT 0 COMMENT '机构id-不能为空'")
    public Long getOrganizationalId() {
        return super.getOrganizationalId();
    }

    @Override
    @Column(name = "user_id",columnDefinition = "bigint NULL DEFAULT 0 COMMENT '机构id-不能为空'")
    public Long getUserId() {
        return super.getUserId();
    }

    @Override
    @Column(unique = true, columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '网格名称-不能为空'")
    public String getName() {
        return super.getName();
    }
}
