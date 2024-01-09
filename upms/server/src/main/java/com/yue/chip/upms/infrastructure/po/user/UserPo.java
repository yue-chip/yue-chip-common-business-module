package com.yue.chip.upms.infrastructure.po.user;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.user.UserDefinition;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * @author Mr.Liu
 * @date 2023/1/10 下午4:17
 * @description UserPo
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = UserPo.TABLE_NAME,indexes = {@Index(columnList = "name"),@Index(columnList = "create_date_time"), @Index(columnList = "update_date_time")})
@SuperBuilder
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
//@Comment("用户")
public class UserPo extends UserDefinition {

    public static final String TABLE_NAME = "t_user";

    @Override
    @Column(updatable = false)
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    @Column(unique = true)
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
    public State getState() {
        return super.getState();
    }

    @Override
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    public Boolean getIsSms() {
        return super.getIsSms();
    }

    @Override
    public Boolean getIsCall() {
        return super.getIsCall();
    }

    @Override
    public LocalDateTime getLastLoginTime() {
        return super.getLastLoginTime();
    }

    @Override
    public boolean isAccountNonExpired() {
        return super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }

    @Override
    public Long getTenantNumber() {
        return super.getTenantNumber();
    }

    public String getEmail() {
        return super.getEmail();
    }

    public IdCardType getIdCardType() {
        return super.getIdCardType();
    }

    public String getCertificateNumber() {
        return super.getCertificateNumber();
    }

    public String getIdentificationNumber() {
        return super.getIdentificationNumber();
    }
}
