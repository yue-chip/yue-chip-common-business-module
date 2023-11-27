package com.yue.chip.upms.infrastructure.po.tenant;

import com.yue.chip.core.BaseDefinition;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.core.persistence.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
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
@Data
@Builder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@Comment("租户状态")
@NoArgsConstructor
@AllArgsConstructor
public class TenantStatePo extends BaseEntity {

    @Column(name = "state" , columnDefinition = "int NULL DEFAULT 1 COMMENT '租户状态-不能为空'")
    private State state;

}
