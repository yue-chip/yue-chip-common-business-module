package com.yue.chip.upms.infrastructure.po.tenant;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.tenant.TenantDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/23 下午1:46
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_tenant",indexes = {@Index(columnList = "manager"),@Index(columnList = "phone_number") })
//@Data
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
//@Comment("租户表")
@NoArgsConstructor
public class TenantPo extends TenantDefinition {
    @Override
    @Column(unique = true)
    public String getName() {
        return super.getName();
    }

    @Override
    @Convert(converter = State.StateConverter.class)
    //@Column(columnDefinition = "int NULL DEFAULT 1 COMMENT '状态(0:禁用,1:正常)-不能为空'")
    public State getState() {
        return super.getState();
    }

    @Override
    @Column( name = "manager")
    public String getManager() {
        return super.getManager();
    }

    @Override
    @Column( name = "phone_number")
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    @Column( name = "request_domain")
    public String getDomain() {
        return super.getDomain();
    }

    @Override
    //@Column( columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '租户名称-简称-不能为空'")
    public String getAbbreviation() {
        return super.getAbbreviation();
    }

    @Override
    @Column(updatable = false)
    public Boolean getIsDefault() {
        return super.getIsDefault();
    }

    @Override
    @Column(unique = true)
    public Long getTenantNumber() {
        return super.getTenantNumber();
    }

    @Override
    //@Column( columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '数字大屏名称-不能为空'")
    public String getBigScreenName() {
        return super.getBigScreenName();
    }
}
