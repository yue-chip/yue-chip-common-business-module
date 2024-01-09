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
@Table(name = "t_user",indexes = {@Index(columnList = "name"),@Index(columnList = "create_date_time"), @Index(columnList = "update_date_time")})
@SuperBuilder
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
//@Comment("用户")
@Data
public class UserPo extends UserDefinition {

    public static final String TABLE_NAME = "t_user";

    @Override
    @Column(updatable = false,name = "password")
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    @Column(unique = true,name = "username")
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    @Column(name = "name")
    public String getName() {
        return super.getName();
    }

    @Override
    @Convert(converter = State.StateConverter.class)
    @Column(name = "state")
    public State getState() {
        return super.getState();
    }

    @Override
    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    @Column(name = "is_sms")
    public Boolean getIsSms() {
        return super.getIsSms();
    }

    @Override
    @Column(name = "is_call")
    public Boolean getIsCall() {
        return super.getIsCall();
    }

    @Override
    @Column(name = "last_login_time")
    public LocalDateTime getLastLoginTime() {
        return super.getLastLoginTime();
    }

    @Override
    @Column(name = "is_account_non_expired")
    public boolean isAccountNonExpired() {
        return super.isAccountNonExpired();
    }

    @Override
    @Column(name = "is_account_non_locked")
    public boolean isAccountNonLocked() {
        return super.isAccountNonLocked();
    }

    @Override
    @Column(name = "is_credentials_non_expired")
    public boolean isCredentialsNonExpired() {
        return super.isCredentialsNonExpired();
    }

    @Override
    @Column(name = "is_enabled")
    public boolean isEnabled() {
        return super.isEnabled();
    }

    @Override
    @Column(name = "tenant_number")
    public Long getTenantNumber() {
        return super.getTenantNumber();
    }

    @Column(name = "email")
    public String getEmail() {
        return super.getEmail();
    }

    @Column(name = "id_card_type")
    public IdCardType getIdCardType() {
        return super.getIdCardType();
    }

    @Column(name = "certificate_number")
    public String getCertificateNumber() {
        return super.getCertificateNumber();
    }

    @Column(name = "identification_number")
    public String getIdentificationNumber() {
        return super.getIdentificationNumber();
    }
}
