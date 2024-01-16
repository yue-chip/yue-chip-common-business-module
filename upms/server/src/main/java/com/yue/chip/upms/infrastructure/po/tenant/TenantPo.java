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
@Table(name = "T_TENANT",indexes = {@Index(columnList = "MANAGER"),@Index(columnList = "PHONE_NUMBER") })
@Data
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
//@Comment("租户表")
@NoArgsConstructor
public class TenantPo extends TenantDefinition {
    @Override
    @Column(unique = true,name = "NAME")
    public String getName() {
        return super.getName();
    }

    @Override
    @Convert(converter = State.StateConverter.class)
    @Column(name = "STATE")
    public State getState() {
        return super.getState();
    }

    @Override
    @Column( name = "MANAGER")
    public String getManager() {
        return super.getManager();
    }

    @Override
    @Column( name = "PHONE_NUMBER")
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    @Column( name = "REQUEST_DOMAIN")
    public String getDomain() {
        return super.getDomain();
    }

    @Override
    @Column( name = "ABBREVIATION")
    public String getAbbreviation() {
        return super.getAbbreviation();
    }

    @Override
    @Column(updatable = false,name = "IS_DEFAULT")
    public Boolean getIsDefault() {
        return super.getIsDefault();
    }

    @Override
    @Column(unique = true,name = "TENANT_NUMBER")
    public Long getTenantNumber() {
        return super.getTenantNumber();
    }

    @Override
    @Column( name = "BIG_SCREEN_NAME")
    public String getBigScreenName() {
        return super.getBigScreenName();
    }
}
