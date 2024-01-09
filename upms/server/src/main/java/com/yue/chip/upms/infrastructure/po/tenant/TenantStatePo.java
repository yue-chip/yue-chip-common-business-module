package com.yue.chip.upms.infrastructure.po.tenant;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.core.persistence.entity.BaseEntity;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/23 上午11:24
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_tenant_state")
@Builder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@Comment("租户状态")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TenantStatePo extends BaseEntity {


    private State state;

    @Comment("租户状态-不能为空")
    @ColumnDefault("1")
    @Convert(converter = State.StateConverter.class)
    public State getState() {
        return state;
    }
}
