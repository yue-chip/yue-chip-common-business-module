package com.xiao.wei.security.infrastructure.po.device;

import com.xiao.wei.definition.tenant.DeviceTenantDefinition;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Mr.Liu
 * 
 * @date 2023/10/27 上午11:26
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_device_tenant", indexes = {@Index(columnList = "sn,tenant_number",unique = true)})
@SuperBuilder
@NoArgsConstructor
@Data
@EntityListeners({AuditingEntityListener.class})
@Comment("设备与租户关联关系表")
public class DeviceTenantPo extends DeviceTenantDefinition {

    @NotNull(message = "设备编码不能为空")
    @ColumnDefault("''")
    @Comment("设备编码")
    public String getSn() {
        return super.getSn();
    }

    @Comment("租户编码")
    public Long getTenantNumber() {
        return super.getTenantNumber();
    }

    @Override
    @ColumnDefault("''")
    @Comment("ctwing token")
    public String getToken() {
        return super.getToken();
    }
}
