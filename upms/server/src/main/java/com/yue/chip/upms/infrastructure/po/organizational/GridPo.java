package com.yue.chip.upms.infrastructure.po.organizational;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.organizational.GridDefinition;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
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
@Comment("网格")
public class GridPo extends GridDefinition {
    @Override
    @Comment("机构id-不能为空")
    @ColumnDefault("0")
    public Long getOrganizationalId() {
        return super.getOrganizationalId();
    }

    @Override
    @Comment("用户id-不能为空")
    @ColumnDefault("0")
    public Long getUserId() {
        return super.getUserId();
    }

    @Override
    @Column(unique = true)
    @Comment("网格名称-不能为空")
    @ColumnDefault("0")
    public String getName() {
        return super.getName();
    }
}
