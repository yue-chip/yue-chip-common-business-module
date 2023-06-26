package com.yue.chip.upms.infrastructure.po.user;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.user.UserDefinition;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

/**
 * @author Mr.Liu
 * @date 2023/1/10 下午4:17
 * @description UserPo
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_user",indexes = {@Index(columnList = "name")})
@SuperBuilder
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
public class UserPo extends UserDefinition {

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    @Column(unique = true)
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public Long getProfilePhoto() {
        return super.getProfilePhoto();
    }

    @Override
    public LocalDate getBirthday() {
        return super.getBirthday();
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
}
