package com.yue.chip.upms.infrastructure.po;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.core.persistence.entity.BaseEntity;
import com.yue.chip.upms.state.machine.test.States;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Mr.Liu
 * @date 2023/8/10 下午2:48
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_test_state_machine")
@SuperBuilder
@NoArgsConstructor
@Data
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@Comment("用户-角色关联表")
public class TestStateMachinePo extends BaseEntity {

    private String name;

    private States states;

    public String getName() {
        return name;
    }

    @Convert(converter = States.StatesConverter.class)
    public States getStates() {
        return states;
    }
}
