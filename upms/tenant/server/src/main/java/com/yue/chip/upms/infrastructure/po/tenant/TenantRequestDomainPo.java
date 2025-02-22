package com.yue.chip.upms.infrastructure.po.tenant;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.core.tenant.common.TenantRequestDomainDefinition;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_tenant_request_domain",indexes = {@Index(columnList = "tenantId"),@Index(columnList = "requestDomain") })
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@Comment("租户表")
@NoArgsConstructor
public class TenantRequestDomainPo extends TenantRequestDomainDefinition {

    @Override
    @NotNull(message = "租户id不能未空")
    public Long getTenantId() {
        return super.getTenantId();
    }

    @Override
    @NotNull(message = "请求域名不能未空")
    public String getRequestDomain() {
        return super.getRequestDomain();
    }
}
