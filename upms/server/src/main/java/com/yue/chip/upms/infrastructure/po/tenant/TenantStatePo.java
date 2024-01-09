package com.yue.chip.upms.infrastructure.po.tenant;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.core.persistence.entity.BaseEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/23 上午11:24
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_tenant_state")
@Data
@Builder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
//@Comment("租户状态")
@NoArgsConstructor
@AllArgsConstructor
public class TenantStatePo extends BaseEntity {


    private State state;

    @Convert(converter = State.StateConverter.class)
    @Column(name = "state")
    public State getState() {
        return state;
    }
}
