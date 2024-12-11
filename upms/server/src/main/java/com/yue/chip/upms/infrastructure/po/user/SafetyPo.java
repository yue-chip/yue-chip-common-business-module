package com.yue.chip.upms.infrastructure.po.user;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.user.SafetyDefinition;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author jiacheng.liao on 2024/12/11
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_safety")
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@Comment("安全设置表")
@NoArgsConstructor
public class SafetyPo extends SafetyDefinition {

    @Override
    @Comment("超时无操作时间设置-不能为空")
    @NotNull
    public Long getTimeout() {
        return super.getTimeout();
    }

    @Override
    @Comment("更新密码时间设置-不能为空")
    @NotNull
    public Long getPasswordTime() {
        return super.getPasswordTime();
    }

}
