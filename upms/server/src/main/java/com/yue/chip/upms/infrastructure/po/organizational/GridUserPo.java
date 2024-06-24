package com.yue.chip.upms.infrastructure.po.organizational;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.organizational.GridUserDefinition;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2024-05-29
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "GRID_USER")
@SuperBuilder
@NoArgsConstructor
@Data
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
//@Comment("网格-用户")
public class GridUserPo extends GridUserDefinition {


    @Override
    @Comment("用户id")
    @NotNull
    @Column(name = "USER_ID")
    public Long getUserId() {
        return super.getUserId();
    }

    @Override
    @Comment("网格id")
    @NotNull
    @Column(name = "GRID_ID")
    public Long getGridId() {
        return super.getGridId();
    }

}
