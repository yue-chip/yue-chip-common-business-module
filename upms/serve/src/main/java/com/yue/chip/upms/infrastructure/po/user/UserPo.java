package com.yue.chip.upms.infrastructure.po.user;

import com.yue.chip.upms.definition.user.UserDefinition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import java.time.LocalDate;

/**
 * @author Mr.Liu
 * @date 2023/1/10 下午4:17
 * @description UserPo
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_user",indexes = {@Index(columnList = "name"),@Index(columnList = "username")})
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@SuperBuilder
@NoArgsConstructor
public class UserPo extends UserDefinition {

    @Override
    @Column(nullable = false)
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    @Column(nullable = false,unique = true)
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    @Column(nullable = false)
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
