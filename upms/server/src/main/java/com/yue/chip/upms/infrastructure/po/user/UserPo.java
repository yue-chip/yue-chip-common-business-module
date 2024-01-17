package com.yue.chip.upms.infrastructure.po.user;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.user.UserDefinition;
import com.yue.chip.upms.enums.IdCardType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Mr.Liu
 * @date 2023/1/10 下午4:17
 * @description UserPo
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "T_USER",indexes = {@Index(columnList = "NAME"),@Index(columnList = "CREATE_DATE_TIME"), @Index(columnList = "UPDATE_DATE_TIME")})
@SuperBuilder
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
//@Comment("用户")
@Data
public class UserPo extends UserDefinition {

    public static final String TABLE_NAME = "T_USER";

    @Override
    @Column(updatable = false,name = "PASSWORD")
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    @Column(unique = true,name = "USERNAME")
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    @Column(name = "NAME")
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
    @Column(name = "PHONE_NUMBER")
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    @Column(name = "IS_SMS")
    public Boolean getIsSms() {
        return super.getIsSms();
    }

    @Override
    @Column(name = "IS_CALL")
    public Boolean getIsCall() {
        return super.getIsCall();
    }

    @Override
    @Column(name = "LAST_LOGIN_TIME")
    public LocalDateTime getLastLoginTime() {
        return super.getLastLoginTime();
    }

    @Override
    @Column(name = "ACCOUNT_NON_EXPIRED")
    public Boolean getAccountNonExpired() {
        return super.getAccountNonExpired();
    }

    @Override
    @Column(name = "ACCOUNT_NON_LOCKED")
    public Boolean getAccountNonLocked() {
        return super.getAccountNonLocked();
    }

    @Override
    @Column(name = "CREDENTIALS_NON_EXPIRED")
    public Boolean getCredentialsNonExpired() {
        return super.getCredentialsNonExpired();
    }

    @Override
    @Column(name = "ENABLED")
    public Boolean getEnabled() {
        return super.getEnabled();
    }

    @Override
    @Column(name = "TENANT_NUMBER")
    public Long getTenantNumber() {
        return super.getTenantNumber();
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return super.getEmail();
    }

    @Column(name = "ID_CARD_TYPE")
    public IdCardType getIdCardType() {
        return super.getIdCardType();
    }

    @Column(name = "CERTIFICATE_NUMBER")
    public String getCertificateNumber() {
        return super.getCertificateNumber();
    }

    @Column(name = "IDENTIFICATION_NUMBER")
    public String getIdentificationNumber() {
        return super.getIdentificationNumber();
    }
}
